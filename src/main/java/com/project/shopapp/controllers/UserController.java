package com.project.shopapp.controllers;

import com.project.shopapp.dtos.requests.SignupRequestDto;
import com.project.shopapp.dtos.responses.UserResponseDto;
import com.project.shopapp.events.SignupCompleteEvent;
import com.project.shopapp.models.User;
import com.project.shopapp.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDto requestDto) {
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
        return MvcUriComponentsBuilder.fromMethodName(AuthController.class, "verifyEmail", "").build().toUriString();
    }

}

