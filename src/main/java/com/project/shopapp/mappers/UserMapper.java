package com.project.shopapp.mappers;

import com.project.shopapp.dtos.requests.SignupRequestDto;
import com.project.shopapp.dtos.responses.UserResponseDto;
import com.project.shopapp.models.Role;
import com.project.shopapp.models.User;
import com.project.shopapp.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ObjectMapperUtils objectMapperUtils;

    public User mapToEntity(SignupRequestDto signupRequestDto, Role role, String encodedPassword) {
        User user = objectMapperUtils.mapToEntityOrDto(signupRequestDto, User.class);
        user.setRole(role);
        user.setPassword(encodedPassword);
        return user;
    }

    public UserResponseDto mapToDto(User user) {
        return objectMapperUtils.mapToEntityOrDto(user, UserResponseDto.class);
    }
}
