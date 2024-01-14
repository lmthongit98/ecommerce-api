package com.project.shopapp.services;

import com.project.shopapp.dtos.requests.SignupRequestDto;
import com.project.shopapp.dtos.requests.UserLoginDto;
import com.project.shopapp.dtos.responses.LoginResponseDto;
import com.project.shopapp.dtos.responses.UserResponseDto;
import com.project.shopapp.models.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    User signup(SignupRequestDto requestDto);

    LoginResponseDto login(UserLoginDto userLoginDto);

    UserResponseDto getUserDetails(Authentication authentication);

    void saveUserVerificationToken(User user, String verificationToken);

    String verifyToken(String token);
}
