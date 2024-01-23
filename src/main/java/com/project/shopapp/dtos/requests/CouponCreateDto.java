package com.project.shopapp.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class CouponCreateDto {

    @NotBlank(message = "couponCode can not be blank")
    private String code;

    @NotBlank(message = "discountAmount can not be blank")
    private Integer discountPercentage;

    @NotBlank(message = "expiryDate can not be blank")
    private LocalDate expiryDate;

    private boolean active;

    private Set<CouponConditionDto> couponConditions;
}
