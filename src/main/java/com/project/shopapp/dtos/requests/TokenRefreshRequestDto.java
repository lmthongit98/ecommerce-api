package com.project.shopapp.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record TokenRefreshRequestDto(
        @NotBlank(message = "refreshToken can bot be blank") String refreshToken
) {
}
