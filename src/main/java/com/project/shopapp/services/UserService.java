package com.project.shopapp.services;

import com.project.shopapp.dtos.requests.UserUpdateDto;
import com.project.shopapp.dtos.responses.UserResponseDto;
import org.springframework.security.core.Authentication;

public interface UserService {

    UserResponseDto getUserDetails(Authentication authentication);

    UserResponseDto updateUser(Authentication authentication, UserUpdateDto userUpdateDto);
}
