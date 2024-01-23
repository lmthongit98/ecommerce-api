package com.project.shopapp.dtos.responses;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponApplyResponseDto {
    private double discountAmount;
}
