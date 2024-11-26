package vn.edu.hust.soict.japango.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.hust.soict.japango.dto.user.AuthenticateRequestDTO;
import vn.edu.hust.soict.japango.dto.user.AuthenticateResponseDTO;
import vn.edu.hust.soict.japango.dto.user.RegisterRequestDTO;
import vn.edu.hust.soict.japango.dto.user.RegisterResponseDTO;
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
}
