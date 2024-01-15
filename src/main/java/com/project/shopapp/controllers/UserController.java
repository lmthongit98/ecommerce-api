package com.project.shopapp.controllers;

import com.project.shopapp.dtos.requests.SignupRequestDto;
import com.project.shopapp.dtos.requests.TokenRefreshRequestDto;
import com.project.shopapp.dtos.requests.UserLoginDto;
import com.project.shopapp.dtos.responses.LoginResponseDto;
import com.project.shopapp.dtos.responses.TokenRefreshResponseDto;
import com.project.shopapp.dtos.responses.UserResponseDto;
import com.project.shopapp.event.SignupCompleteEvent;
import com.project.shopapp.exceptions.TokenRefreshException;
import com.project.shopapp.models.RefreshToken;
import com.project.shopapp.models.User;
import com.project.shopapp.services.UserService;
import com.project.shopapp.services.impl.RefreshTokenService;
import com.project.shopapp.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDto requestDto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        User user = userService.signup(requestDto);
        publisher.publishEvent(new SignupCompleteEvent(user, getVerifyEmailUrl()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/details")
    public ResponseEntity<?> getUserDetails(Authentication authentication) {
        UserResponseDto userResponseDto = userService.getUserDetails(authentication);
        return ResponseEntity.ok(userResponseDto);
    }

    private String getVerifyEmailUrl() {
        return MvcUriComponentsBuilder.fromMethodName(UserController.class, "verifyEmail", "").build().toUriString();
    }

}

