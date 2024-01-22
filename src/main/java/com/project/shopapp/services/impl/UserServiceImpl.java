package com.project.shopapp.services.impl;

import com.project.shopapp.dtos.requests.SignupRequestDto;
import com.project.shopapp.dtos.requests.UserUpdateDto;
import com.project.shopapp.dtos.responses.UserResponseDto;
import com.project.shopapp.exceptions.BadRequestException;
import com.project.shopapp.exceptions.DuplicateException;
import com.project.shopapp.exceptions.ResourceNotFoundException;
import com.project.shopapp.mappers.UserMapper;
import com.project.shopapp.models.Role;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto getUserDetails(Authentication authentication) {
        String email = ((User) authentication.getPrincipal()).getEmail();
        User user = getUserWithRoleAndPermissions(email);
        return userMapper.mapToDto(user);
    }

    @Override
    public UserResponseDto updateUser(Authentication authentication, UserUpdateDto userUpdateDto) {
        String email = ((User) authentication.getPrincipal()).getEmail();
        //todo: implement update user profile
        return null;
    }

    private User getUserWithRoleAndPermissions(String email) {
        return userRepository.findUserWithRoleAndPermissions(email).orElseThrow(() -> new ResourceNotFoundException("User could not be found with email: " + email));
    }

}
