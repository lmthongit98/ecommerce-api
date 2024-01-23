package com.project.shopapp.services;

import com.project.shopapp.dtos.requests.CouponRequestDto;
import com.project.shopapp.dtos.requests.CouponApplyRequestDto;
import com.project.shopapp.dtos.responses.*;

import java.util.List;

public interface CouponService {
    CouponApplyResponseDto applyCoupon(CouponApplyRequestDto couponApplyRequestDto);

    CouponResponseDto createCoupon(CouponRequestDto couponRequestDto);

    List<AttributeResponseDto> getAttributes();

    List<String> getOperators();

    CouponResponseDto getCouponById(Long id);

    PagingResponseDto<CouponResponseDto> getCoupons(String searchKey, int pageNo, int pageSize, String sortBy, String sortDir);

    Object updateCoupon(Long id, CouponRequestDto couponRequestDto);

    void deleteCoupon(Long id);
}
