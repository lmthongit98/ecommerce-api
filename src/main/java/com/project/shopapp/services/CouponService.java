package com.project.shopapp.services;

import com.project.shopapp.dtos.requests.CouponCreateDto;
import com.project.shopapp.dtos.requests.CouponRequestDto;
import com.project.shopapp.dtos.responses.CouponResponseDto;

public interface CouponService {
    CouponResponseDto applyCoupon(CouponRequestDto couponRequestDto);

    void createCoupon(CouponCreateDto couponCreateDto);
}
