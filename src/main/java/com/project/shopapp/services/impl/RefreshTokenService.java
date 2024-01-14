package com.project.shopapp.services.impl;

import com.project.shopapp.dtos.responses.TokenRefreshResponseDto;
import com.project.shopapp.exceptions.TokenRefreshException;
import com.project.shopapp.models.RefreshToken;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.RefreshTokenRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Value("${jwt.expiration-refresh-token-milliseconds}")
    private long refreshTokenExpirationDate;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.isTokenExpired()) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    public RefreshToken createRefreshToken(User user) {
        RefreshToken existingToken = refreshTokenRepository.findTokenByUser(user);
        if (existingToken != null) {
            refreshTokenRepository.delete(existingToken);
        }
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + refreshTokenExpirationDate);
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .expiryDate(expireDate)
                .token(UUID.randomUUID().toString())
                .build();
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public TokenRefreshResponseDto refreshToken(String requestRefreshToken) {
        return findByToken(requestRefreshToken)
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateToken(user.getUsername());
                    RefreshToken newRefreshToken = createRefreshToken(user);
                    return new TokenRefreshResponseDto(token, newRefreshToken.getToken());
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
    }
}
