package com.project.shopapp.controllers;

import com.project.shopapp.dtos.requests.CouponCreateDto;
import com.project.shopapp.dtos.requests.CouponRequestDto;
import com.project.shopapp.dtos.responses.GenericResponse;
import com.project.shopapp.services.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/apply")
    public ResponseEntity<?> applyCoupon(@RequestBody CouponRequestDto couponRequestDto) {
        var couponResponse = couponService.applyCoupon(couponRequestDto);
        return ResponseEntity.ok(couponResponse);
    }

    @PostMapping
    public ResponseEntity<?> createCoupon(@RequestBody CouponCreateDto couponCreateDto) {
        couponService.createCoupon(couponCreateDto);
        return ResponseEntity.ok(GenericResponse.empty());
    }

    @GetMapping("/attributes")
    public ResponseEntity<?> getAttributes() {
        return ResponseEntity.ok(GenericResponse.success(couponService.getAttributes()));
    }

    @GetMapping("/operators")
    public ResponseEntity<?> getOperators() {
        return ResponseEntity.ok(GenericResponse.success(couponService.getOperators()));
    }


}
