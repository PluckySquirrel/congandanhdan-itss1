package vn.edu.hust.soict.japango.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.hust.soict.japango.common.constants.TemplateParams;
import vn.edu.hust.soict.japango.common.enums.Mail;
import vn.edu.hust.soict.japango.common.enums.TokenType;
import vn.edu.hust.soict.japango.common.utils.StringUtils;
import vn.edu.hust.soict.japango.dto.user.*;
import vn.edu.hust.soict.japango.entity.Token;
import vn.edu.hust.soict.japango.entity.User;
import vn.edu.hust.soict.japango.exception.CustomExceptions;
import vn.edu.hust.soict.japango.exception.ResourceNotFoundException;
import vn.edu.hust.soict.japango.repository.TokenRepository;
import vn.edu.hust.soict.japango.repository.UserRepository;
import vn.edu.hust.soict.japango.service.MailService;
import vn.edu.hust.soict.japango.service.UserService;
import vn.edu.hust.soict.japango.service.mapper.UserMapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final TokenRepository tokenRepository;
    private final MailService mailService;
    private final DateTimeFormatter dateTimeFormatter;

    @Value("${app.security.secret-key}")
    private String secretKey;

    @Value("${app.token.length}")
    private Integer tokenLength;

    @Value("${app.token.reset-password.expire-seconds}")
    private Long resetPasswordTokenExpireSeconds;

    @Value("${app.web.url.reset-password}")
    private String resetPasswordUrl;

    @Override
    public AuthenticateResponseDTO authenticate(AuthenticateRequestDTO request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> CustomExceptions.USER_NOT_EXISTS_EXCEPTION);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw CustomExceptions.INCORRECT_PASSWORD_EXCEPTION;
        }

        String accessToken = generateAccessToken(user);
        return AuthenticateResponseDTO.builder()
                .accessToken(accessToken)
                .build();
    }

    private String generateAccessToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(24, ChronoUnit.HOURS).toEpochMilli()))
                .claim("id", user.getId())
                .claim("uuid", user.getUuid())
                .claim("name", user.getName())
                .claim("email", user.getEmail())
                .claim("scope", user.getRole().name())
                .build();
        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(secretKey));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw CustomExceptions.USERNAME_USED_EXCEPTION;
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw CustomExceptions.EMAIL_USED_EXCEPTION;
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return userMapper.toRegisterResponseDTO(user);
    }

    @Override
    public GetProfileResponseDTO getProfile(String uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("User", "uuid", uuid));

        return userMapper.toGetProfileResponseDTO(user);
    }

    @Override
    public UpdateProfileResponseDTO updateProfile(String uuid, UpdateProfileRequestDTO request) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("User", "uuid", uuid));

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw CustomExceptions.USERNAME_USED_EXCEPTION;
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw CustomExceptions.EMAIL_USED_EXCEPTION;
        }

        userMapper.updateEntity(user, request);
        userRepository.save(user);

        return userMapper.toUpdateProfileResponseDTO(user);
    }

    @Override
    public ChangePasswordResponseDTO changePassword(String uuid, ChangePasswordRequestDTO request) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("User", "uuid", uuid));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw CustomExceptions.INCORRECT_PASSWORD_EXCEPTION;
        }

        if (request.getNewPassword().equals(request.getOldPassword())) {
            throw CustomExceptions.NEW_PASSWORD_SAME_AS_OLD_PASSWORD_EXCEPTION;
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return userMapper.toChangePasswordResponseDTO(user);
    }

    @Override
    public ForgotPasswordResponseDTO forgotPassword(ForgotPasswordRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", request.getEmail()));

        LocalDateTime timestamp = LocalDateTime.now();

        tokenRepository.deleteByUserIdAndTypeAndExpireTimeAfterAndIsNotUsed(user.getId(), TokenType.RESET_PASSWORD, timestamp);
        String tokenValue;
        do {
            tokenValue = generateAppToken();
        } while (tokenRepository.findByValueAndExpireTimeAfterAndIsNotUsed(tokenValue, timestamp).isPresent());
        Token token = Token.builder()
                .userId(user.getId())
                .type(TokenType.RESET_PASSWORD)
                .value(tokenValue)
                .expireTime(timestamp.plusSeconds(resetPasswordTokenExpireSeconds))
                .build();
        tokenRepository.save(token);

        mailService.sendMail(user.getEmail(), Mail.RESET_PASSWORD_MAIL, Map.of(
                TemplateParams.NAME, user.getName(),
                TemplateParams.TIMESTAMP, timestamp.format(dateTimeFormatter),
                TemplateParams.URL, StringUtils.replaceArg(resetPasswordUrl, TemplateParams.TOKEN, token.getValue())
        ));

        return ForgotPasswordResponseDTO.builder().expireSeconds(resetPasswordTokenExpireSeconds).build();
    }

    private String generateAppToken() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder token = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < tokenLength; i++) {
            token.append(characters.charAt(random.nextInt(characters.length())));
        }
        return token.toString();
    }

    @Override
    public VerifyTokenResponseDTO verifyToken(VerifyTokenRequestDTO request) {
        Optional<Token> tokenOptional = tokenRepository
                .findByValueAndExpireTimeAfterAndIsNotUsed(request.getToken(), LocalDateTime.now());
        return VerifyTokenResponseDTO.builder()
                .isValid(tokenOptional.isPresent())
                .build();
    }

    @Override
    public ResetPasswordResponseDTO resetPassword(ResetPasswordRequestDTO request) {
        Token token = tokenRepository.findByValueAndExpireTimeAfterAndIsNotUsed(request.getToken(), LocalDateTime.now())
                .orElseThrow(() -> CustomExceptions.TOKEN_INVALID_EXCEPTION);

        User user = userRepository.findById(token.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", token.getUserId()));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        token.setIsUsed(true);
        tokenRepository.save(token);

        return userMapper.toResetPasswordResponseDTO(user);
    }
}
