package com.project.shopapp.controllers;

import com.project.shopapp.dtos.requests.TokenRefreshRequestDto;
import com.project.shopapp.dtos.requests.UserLoginDto;
import com.project.shopapp.dtos.responses.LoginResponseDto;
import com.project.shopapp.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto userLoginDto) {
        LoginResponseDto loginResponseDto = authService.login(userLoginDto);
        return ResponseEntity.ok(loginResponseDto);
    }

    @GetMapping("/verifyEmail")
    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token) {
        String result = authService.verifyToken(token);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequestDto request) {
        String requestRefreshToken = request.refreshToken();
        var tokenRefreshResponseDto = authService.refreshToken(requestRefreshToken);
        return ResponseEntity.ok(tokenRefreshResponseDto);
    }

}
