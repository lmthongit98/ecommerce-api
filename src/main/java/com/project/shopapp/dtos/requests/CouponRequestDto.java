package com.project.shopapp.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CouponRequestDto {
    private String couponCode;
    private Double totalAmount;
    private List<CartItemDTO> cartItems;
}
