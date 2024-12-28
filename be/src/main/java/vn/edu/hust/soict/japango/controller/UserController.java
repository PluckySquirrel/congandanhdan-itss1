package vn.edu.hust.soict.japango.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hust.soict.japango.dto.user.*;
import vn.edu.hust.soict.japango.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    ResponseEntity<AuthenticateResponseDTO> authenticate(@RequestBody @Valid AuthenticateRequestDTO request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @PostMapping("/register")
    ResponseEntity<RegisterResponseDTO> register(@RequestBody @Valid RegisterRequestDTO request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/{uuid}")
    ResponseEntity<GetProfileResponseDTO> getProfile(@PathVariable String uuid) {
        return ResponseEntity.ok(userService.getProfile(uuid));
    }

    @PutMapping("/{uuid}")
    ResponseEntity<UpdateProfileResponseDTO> updateProfile(
            @PathVariable String uuid,
            @RequestBody @Valid UpdateProfileRequestDTO request
    ) {
        return ResponseEntity.ok(userService.updateProfile(uuid, request));
    }

    @PutMapping("/{uuid}/change-password")
    ResponseEntity<ChangePasswordResponseDTO> changePassword(
            @PathVariable String uuid,
            @RequestBody @Valid ChangePasswordRequestDTO request
    ) {
        return ResponseEntity.ok(userService.changePassword(uuid, request));
    }

    @PostMapping("/forgot-password")
    ResponseEntity<ForgotPasswordResponseDTO> forgotPassword(@RequestBody @Valid ForgotPasswordRequestDTO request) {
        return ResponseEntity.ok(userService.forgotPassword(request));
    }

    @PostMapping("/verify-token")
    ResponseEntity<VerifyTokenResponseDTO> verifyToken(@RequestBody @Valid VerifyTokenRequestDTO request) {
        return ResponseEntity.ok(userService.verifyToken(request));
    }

    @PutMapping("/reset-password")
    ResponseEntity<ResetPasswordResponseDTO> resetPassword(@RequestBody @Valid ResetPasswordRequestDTO request) {
        return ResponseEntity.ok(userService.resetPassword(request));
    }

    @PostMapping("/{uuid}/upload-avatar")
    public ResponseEntity<UploadAvatarResponseDTO> uploadImage(@PathVariable String uuid, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(userService.uploadAvatar(uuid, file));
    }
}
