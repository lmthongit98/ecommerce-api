package com.project.shopapp.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.shopapp.dtos.requests.CouponConditionDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class CouponResponseDto {
    private Long id;
    private String code;
    private Integer discountPercentage;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expiryDate;
    private boolean active;
    private Set<CouponConditionDto> couponConditions;
}
