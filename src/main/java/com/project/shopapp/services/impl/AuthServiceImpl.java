package com.project.shopapp.services.impl;

import com.project.shopapp.dtos.requests.UserLoginDto;
import com.project.shopapp.dtos.responses.LoginResponseDto;
import com.project.shopapp.dtos.responses.TokenRefreshResponseDto;
import com.project.shopapp.dtos.responses.UserResponseDto;
import com.project.shopapp.exceptions.ResourceNotFoundException;
import com.project.shopapp.exceptions.TokenRefreshException;
import com.project.shopapp.mappers.UserMapper;
import com.project.shopapp.models.RefreshToken;
import com.project.shopapp.models.User;
import com.project.shopapp.models.VerificationToken;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.repositories.VerificationTokenRepository;
import com.project.shopapp.services.AuthService;
import com.project.shopapp.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final UserMapper userMapper;
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public LoginResponseDto login(UserLoginDto userLoginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.email(), userLoginDto.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(userLoginDto.email());
        User user = getUserWithRoleAndPermissions(userLoginDto.email());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        UserResponseDto userResponseDto = userMapper.mapToDto(user);
        return LoginResponseDto.builder()
                .token(token)
                .refreshToken(refreshToken.getToken())
                .userInfo(userResponseDto)
                .build();
    }

    @Override
    public String verifyToken(String token) {
        String verificationResult = validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")) {
            return "Verified successfully!. You can login to the website!";
        }
        return "Fail to verify, the verification code is not correct!";
    }

    @Override
    public TokenRefreshResponseDto refreshToken(String requestRefreshToken) {
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateToken(user.getUsername());
                    RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);
                    return new TokenRefreshResponseDto(token, newRefreshToken.getToken());
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
    }

    @Override
    public void saveUserVerificationToken(User user, String token) {
        var verificationToken = new VerificationToken(token, user);
        verificationTokenRepository.save(verificationToken);
    }

    private String validateToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            String msg = "Invalid verification verificationToken";
            log.info(msg);
            return msg;
        }
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            verificationTokenRepository.delete(verificationToken);
            String msg = "Invalid verification verificationToken";
            log.info(msg);
            return msg;
        }
        user.setActive(true);
        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken);
        return "valid";
    }

    private User getUserWithRoleAndPermissions(String email) {
        return userRepository.findUserWithRoleAndPermissions(email).orElseThrow(() -> new ResourceNotFoundException("User could not be found with email: " + email));
    }

}
