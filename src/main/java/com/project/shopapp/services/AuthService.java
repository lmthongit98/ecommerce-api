package com.project.shopapp.services;

import com.project.shopapp.dtos.requests.SignupRequestDto;
import com.project.shopapp.dtos.requests.UserLoginDto;
import com.project.shopapp.dtos.responses.LoginResponseDto;
import com.project.shopapp.dtos.responses.TokenRefreshResponseDto;
import com.project.shopapp.models.User;

public interface AuthService {

    LoginResponseDto login(UserLoginDto userLoginDto);

    String verifyToken(String token);

    TokenRefreshResponseDto refreshToken(String requestRefreshToken);

    void saveUserVerificationToken(User user, String verificationToken);

    User signup(SignupRequestDto requestDto);
}
