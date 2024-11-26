package vn.edu.hust.soict.japango.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.hust.soict.japango.dto.user.AuthenticateRequestDTO;
import vn.edu.hust.soict.japango.dto.user.AuthenticateResponseDTO;
import vn.edu.hust.soict.japango.dto.user.RegisterRequestDTO;
import vn.edu.hust.soict.japango.dto.user.RegisterResponseDTO;
import vn.edu.hust.soict.japango.entity.User;
import vn.edu.hust.soict.japango.exception.CustomExceptions;
import vn.edu.hust.soict.japango.repository.UserRepository;
import vn.edu.hust.soict.japango.service.UserService;
import vn.edu.hust.soict.japango.service.mapper.UserMapper;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Value("${app.secret-key}")
    private String secretKey;

    @Override
    public AuthenticateResponseDTO authenticate(AuthenticateRequestDTO request) {
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
        if (userOptional.isEmpty()) {
            throw CustomExceptions.USER_NOT_EXISTS_EXCEPTION;
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw CustomExceptions.INCORRECT_PASSWORD_EXCEPTION;
        }

        String accessToken = generateToken(user);
        return AuthenticateResponseDTO.builder()
                .accessToken(accessToken)
                .build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(24, ChronoUnit.HOURS).toEpochMilli()))
                .claim("uuid", user.getUuid())
                .claim("scope", user.getRole().name())
                .claim("name", user.getName())
                .claim("email", user.getEmail())
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
}
