package com.project.shopapp.services.impl;

import com.project.shopapp.dtos.requests.CartItemDTO;
import com.project.shopapp.dtos.requests.CouponCreateDto;
import com.project.shopapp.dtos.requests.CouponRequestDto;
import com.project.shopapp.dtos.responses.AttributeResponseDto;
import com.project.shopapp.dtos.responses.CouponResponseDto;
import com.project.shopapp.enums.Attribute;
import com.project.shopapp.enums.Operator;
import com.project.shopapp.exceptions.BadRequestException;
import com.project.shopapp.exceptions.ResourceNotFoundException;
import com.project.shopapp.mappers.CouponMapper;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.Coupon;
import com.project.shopapp.models.CouponCondition;
import com.project.shopapp.models.Product;
import com.project.shopapp.repositories.CouponRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.services.CouponService;
import com.project.shopapp.dtos.coupon.CouponConditionFactory;
import com.project.shopapp.dtos.coupon.conditions.GenericCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final ProductRepository productRepository;
    private final CouponMapper couponMapper;

    @Override
    public CouponResponseDto applyCoupon(CouponRequestDto couponRequestDto) {
        CouponResponseDto couponResponseDto = new CouponResponseDto();
        Coupon coupon = couponRepository.findByCodeAndActive(couponRequestDto.getCouponCode(), true).orElseThrow(() -> new ResourceNotFoundException("Coupon is not valid: " + couponRequestDto.getCouponCode()));
        if (coupon.isExpired()) {
            throw new BadRequestException("Coupon is expired!");
        }
        Set<CouponCondition> couponConditions = coupon.getCouponConditions();
        Map<Attribute, Object> attributeMap = getConditionAttributeMap(couponRequestDto);
        List<GenericCondition> conditions = getConditions(couponConditions, attributeMap);
        Pair<Boolean, String> conditionMeet = checkConditionMeet(conditions);
        if (!conditionMeet.getFirst()) {
            throw new BadRequestException(conditionMeet.getSecond());
        }
        calculateDiscount(couponRequestDto, coupon, couponResponseDto);
        return couponResponseDto;
    }

    @Override
    public void createCoupon(CouponCreateDto couponCreateDto) {
        couponRepository.save(couponMapper.mapToEntity(couponCreateDto));
    }

    @Override
    public List<AttributeResponseDto> getAttributes() {
        return Arrays.stream(Attribute.values()).map(attribute -> new AttributeResponseDto(attribute.name(), attribute.getName())).toList();
    }

    @Override
    public List<String> getOperators() {
        return Arrays.stream(Operator.values()).map(Enum::name).toList();
    }

    private void calculateDiscount(CouponRequestDto couponRequestDto, Coupon coupon, CouponResponseDto couponResponseDto) {
        double discountPercentage = (double) coupon.getDiscountPercentage() / 100;
        double updatedAmount = couponRequestDto.getTotalAmount() - (discountPercentage * couponRequestDto.getTotalAmount());
        couponResponseDto.setDiscountAmount(updatedAmount);
    }

    private Pair<Boolean, String> checkConditionMeet(List<GenericCondition> conditions) {
        for (GenericCondition condition : conditions) {
            if (!condition.isConditionMeet()) {
                return Pair.of(false, condition.getInvalidMessage());
            }
        }
        return Pair.of(true, "");
    }

    private List<GenericCondition> getConditions(Set<CouponCondition> couponConditions, Map<Attribute, Object> attributeMap) {
        List<GenericCondition> genericConditions = new ArrayList<>();
        for (CouponCondition couponCondition : couponConditions) {
            Object attributeValue = attributeMap.get(couponCondition.getAttribute());
            GenericCondition condition = CouponConditionFactory.createCouponCondition(couponCondition, attributeValue);
            genericConditions.add(condition);
        }
        return genericConditions;
    }

    private Map<Attribute, Object> getConditionAttributeMap(CouponRequestDto couponRequestDto) {
        Map<Attribute, Object> attributeMap = new HashMap<>();
        attributeMap.put(Attribute.TOTAL_AMOUNT, couponRequestDto.getTotalAmount());
        attributeMap.put(Attribute.PURCHASE_DATE, LocalDate.now());
        attributeMap.put(Attribute.CATEGORIES, getProductCategories(couponRequestDto));
        return attributeMap;
    }

    private String[] getProductCategories(CouponRequestDto couponRequestDto) {
        List<Long> productIds = couponRequestDto.getCartItems().stream().map(CartItemDTO::getProductId).toList();
        List<Product> products = productRepository.findByIds(productIds);
        if (products.size() != productIds.size()) {
            throw new BadRequestException("Invalid cart items");
        }
        return products.stream().map(Product::getCategory).map(Category::getName).toArray(String[]::new);
    }

}
