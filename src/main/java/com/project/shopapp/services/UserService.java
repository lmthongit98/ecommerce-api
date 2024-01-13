package com.project.shopapp.services;

import com.project.shopapp.dtos.requests.SignupRequestDto;
import com.project.shopapp.dtos.requests.UserLoginDto;
import com.project.shopapp.dtos.responses.LoginResponseDto;
import com.project.shopapp.dtos.responses.UserResponseDto;
import org.springframework.security.core.Authentication;

public interface UserService {
    void signup(SignupRequestDto requestDto);

    LoginResponseDto login(UserLoginDto userLoginDto);

    UserResponseDto getUserDetails(Authentication authentication);
}
