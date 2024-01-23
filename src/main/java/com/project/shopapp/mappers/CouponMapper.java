package com.project.shopapp.mappers;

import com.project.shopapp.dtos.requests.CouponRequestDto;
import com.project.shopapp.dtos.responses.CouponResponseDto;
import com.project.shopapp.models.Coupon;
import com.project.shopapp.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponMapper {

    private final ObjectMapperUtils objectMapperUtils;

    public Coupon mapToEntity(CouponRequestDto couponRequestDto) {
        Coupon coupon = objectMapperUtils.mapToEntityOrDto(couponRequestDto, Coupon.class);
        coupon.getCouponConditions().forEach(condition -> condition.setCoupon(coupon));
        return coupon;
    }

    public void mapToEntity(Coupon coupon, CouponRequestDto couponRequestDto) {
        objectMapperUtils.mapToPersistedEntity(coupon, couponRequestDto);
    }

    public CouponResponseDto mapToDto(Coupon coupon) {
        return objectMapperUtils.mapToEntityOrDto(coupon, CouponResponseDto.class);
    }
}
