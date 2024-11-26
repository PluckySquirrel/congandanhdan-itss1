package vn.edu.hust.soict.japango.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
}
