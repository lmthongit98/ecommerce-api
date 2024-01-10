package com.project.shopapp.dtos.responses;

public record ApiErrorResponse(
//        @Schema(description = "Error code")
        int errorCode,
//        @Schema(description = "Error description")
        String description) {

}