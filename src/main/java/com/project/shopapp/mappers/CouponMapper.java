package com.project.shopapp.mappers;

import com.project.shopapp.dtos.requests.CouponCreateDto;
import com.project.shopapp.models.Coupon;
import com.project.shopapp.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponMapper {

    private final ObjectMapperUtils objectMapperUtils;

    public Coupon mapToEntity(CouponCreateDto couponCreateDto) {
        Coupon coupon = objectMapperUtils.mapToEntityOrDto(couponCreateDto, Coupon.class);
        coupon.getCouponConditions().forEach(condition -> condition.setCoupon(coupon));
        return coupon;
    }

}
