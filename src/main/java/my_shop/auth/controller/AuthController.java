package my_shop.auth.controller;

import jakarta.validation.Valid;
import my_shop.auth.dto.AuthResponseDto;
import my_shop.auth.dto.LoginRequestDto;
import my_shop.auth.dto.RegisterRequestDto;
import my_shop.auth.service.AuthService;
import my_shop.users.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> registerUser(@Valid @RequestBody RegisterRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.userRegister(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> loginUser(@Valid @RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(authService.userLogin(request));
    }

    @GetMapping("/check-status")
    public ResponseEntity<AuthResponseDto> checkStatus(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(authService.checkStatus(user));
    }
}
