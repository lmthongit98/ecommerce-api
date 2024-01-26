package com.project.shopapp.controllers;

import com.project.shopapp.constants.AppConstants;
import com.project.shopapp.dtos.requests.CouponApplyRequestDto;
import com.project.shopapp.dtos.requests.CouponRequestDto;
import com.project.shopapp.services.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping
    public ResponseEntity<?> getCoupons(
            @RequestParam(value = "search_key", defaultValue = AppConstants.EMPTY, required = false) String searchKey,
            @RequestParam(value = "page_no", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "page_size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sort_by", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sort_dir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return ResponseEntity.ok(couponService.getCoupons(searchKey, pageNo, pageSize, sortBy, sortDir));
    }

    @PostMapping("/apply")
    public ResponseEntity<?> applyCoupon(@RequestBody CouponApplyRequestDto couponApplyRequestDto) {
        return ResponseEntity.ok(couponService.applyCoupon(couponApplyRequestDto));
    }

    @PostMapping
    public ResponseEntity<?> createCoupon(@RequestBody CouponRequestDto couponRequestDto) {
        return ResponseEntity.ok(couponService.createCoupon(couponRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCoupon(@PathVariable Long id, @RequestBody CouponRequestDto couponRequestDto) {
        return ResponseEntity.ok(couponService.updateCoupon(id, couponRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/attributes")
    public ResponseEntity<?> getAttributes() {
        return ResponseEntity.ok(couponService.getAttributes());
    }

    @GetMapping("/operators")
    public ResponseEntity<?> getOperators() {
        return ResponseEntity.ok(couponService.getOperators());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCouponById(@PathVariable Long id) {
        return ResponseEntity.ok(couponService.getCouponById(id));
    }

}
