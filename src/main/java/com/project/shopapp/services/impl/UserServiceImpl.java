package com.project.shopapp.services.impl;

import com.project.shopapp.dtos.requests.SignupRequestDto;
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
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User signup(SignupRequestDto signupRequestDto) {
        String email = signupRequestDto.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateException("Email is already exist!");
        }
        Role userRole = roleRepository.findByName(Role.USER).orElseThrow(() -> new ResourceNotFoundException("User role could not be found"));
        if (!signupRequestDto.getPassword().equals(signupRequestDto.getRetypePassword())) {
            throw new BadRequestException("Password does not match!");
        }
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());
        User newUser = userMapper.mapToEntity(signupRequestDto, userRole, encodedPassword);
        return userRepository.save(newUser);
    }

    @Override
    public UserResponseDto getUserDetails(Authentication authentication) {
        String email = ((User) authentication.getPrincipal()).getEmail();
        User user = getUserWithRoleAndPermissions(email);
        return userMapper.mapToDto(user);
    }

    private User getUserWithRoleAndPermissions(String email) {
        return userRepository.findUserWithRoleAndPermissions(email).orElseThrow(() -> new ResourceNotFoundException("User could not be found with email: " + email));
    }

}
