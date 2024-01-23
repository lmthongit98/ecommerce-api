package com.project.shopapp.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class CouponRequestDto {

    @NotBlank(message = "couponCode can not be blank")
    private String code;

    @NotBlank(message = "discountAmount can not be blank")
    private Integer discountPercentage;

    @NotNull(message = "expiryDate can not be blank")
    private LocalDate expiryDate;

    private boolean active;

    @JsonProperty("conditions")
    private Set<CouponConditionDto> couponConditions;
}
