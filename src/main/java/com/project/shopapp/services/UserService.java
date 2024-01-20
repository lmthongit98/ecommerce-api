package com.project.shopapp.services;

import com.project.shopapp.dtos.requests.SignupRequestDto;
import com.project.shopapp.dtos.requests.UserUpdateDto;
import com.project.shopapp.dtos.responses.UserResponseDto;
import com.project.shopapp.models.User;
import org.springframework.security.core.Authentication;

public interface UserService {

    User signup(SignupRequestDto requestDto);

    UserResponseDto getUserDetails(Authentication authentication);

    UserResponseDto updateUser(Authentication authentication, UserUpdateDto userUpdateDto);
}
