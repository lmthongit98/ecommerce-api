package com.project.shopapp.services.impl;

import com.project.shopapp.dtos.coupon.CouponConditionFactory;
import com.project.shopapp.dtos.coupon.conditions.GenericCondition;
import com.project.shopapp.dtos.coupon.conditions.composites.AndComposite;
import com.project.shopapp.dtos.coupon.visitors.impl.ConditionEvaluatorVisitor;
import com.project.shopapp.dtos.requests.CartItemDTO;
import com.project.shopapp.dtos.requests.CouponApplyRequestDto;
import com.project.shopapp.dtos.requests.CouponRequestDto;
import com.project.shopapp.dtos.responses.AttributeResponseDto;
import com.project.shopapp.dtos.responses.CouponApplyResponseDto;
import com.project.shopapp.dtos.responses.CouponResponseDto;
import com.project.shopapp.dtos.responses.PagingResponseDto;
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
import com.project.shopapp.utils.PageableUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final ProductRepository productRepository;
    private final CouponMapper couponMapper;

    @Override
    public CouponApplyResponseDto applyCoupon(CouponApplyRequestDto couponApplyRequestDto) {
        CouponApplyResponseDto couponApplyResponseDto = new CouponApplyResponseDto();
        Coupon coupon = couponRepository.findByCodeAndActive(couponApplyRequestDto.getCouponCode(), true).orElseThrow(() -> new ResourceNotFoundException("Coupon is not valid: " + couponApplyRequestDto.getCouponCode()));
        if (coupon.isExpired()) {
            throw new BadRequestException("Coupon is expired!");
        }
        Set<CouponCondition> couponConditions = coupon.getCouponConditions();
        Map<Attribute, Object> attributeMap = getConditionAttributeMap(couponApplyRequestDto);
        List<GenericCondition> conditions = getConditions(couponConditions, attributeMap);
        Pair<Boolean, String> conditionMeet = checkConditionMeet(conditions);
        if (!conditionMeet.getFirst()) {
            throw new BadRequestException(conditionMeet.getSecond());
        }
        calculateDiscount(couponApplyRequestDto, coupon, couponApplyResponseDto);
        return couponApplyResponseDto;
    }

    @Override
    public CouponResponseDto createCoupon(CouponRequestDto couponRequestDto) {
        return couponMapper.mapToDto(couponRepository.save(couponMapper.mapToEntity(couponRequestDto)));
    }

    @Override
    public List<AttributeResponseDto> getAttributes() {
        return Arrays.stream(Attribute.values()).map(attribute -> new AttributeResponseDto(attribute.name(), attribute.getName())).toList();
    }

    @Override
    public List<String> getOperators() {
        return Arrays.stream(Operator.values()).map(Enum::name).toList();
    }

    @Override
    public CouponResponseDto getCouponById(Long id) {
        return couponMapper.mapToDto(getCoupon(id));
    }

    @Override
    public PagingResponseDto<CouponResponseDto> getCoupons(String searchKey, int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable pageable = PageableUtils.getPageable(pageNo, pageSize, sortBy, sortDir);
        Page<Coupon> coupons = couponRepository.findByKeyword(searchKey, pageable);
        List<Coupon> listOfCoupons = coupons.getContent();
        List<CouponResponseDto> content = listOfCoupons.stream().map(couponMapper::mapToDto).collect(Collectors.toList());
        return new PagingResponseDto<>(coupons, content);
    }

    @Override
    public Object updateCoupon(Long id, CouponRequestDto couponRequestDto) {
        Coupon coupon = getCoupon(id);
        couponMapper.mapToEntity(coupon, couponRequestDto);
        return couponMapper.mapToDto(couponRepository.save(coupon));
    }

    @Override
    public void deleteCoupon(Long id) {
        Coupon coupon = getCoupon(id);
        couponRepository.delete(coupon);
    }


    private Coupon getCoupon(Long id) {
        return couponRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Coupon", id));
    }

    private void calculateDiscount(CouponApplyRequestDto couponApplyRequestDto, Coupon coupon, CouponApplyResponseDto couponApplyResponseDto) {
        double discountPercentage = (double) coupon.getDiscountPercentage() / 100;
        double updatedAmount = couponApplyRequestDto.getTotalAmount() - (discountPercentage * couponApplyRequestDto.getTotalAmount());
        couponApplyResponseDto.setDiscountAmount(updatedAmount);
    }

    private Pair<Boolean, String> checkConditionMeet(List<GenericCondition> conditions) {
        ConditionEvaluatorVisitor visitor = new ConditionEvaluatorVisitor();
        AndComposite andComposite = new AndComposite(conditions);
        andComposite.accept(visitor);
        return Pair.of(visitor.isConditionMeet(), visitor.getUnsatisfiedMessages()); // todo: correct messages accordingly
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

    private Map<Attribute, Object> getConditionAttributeMap(CouponApplyRequestDto couponApplyRequestDto) {
        Map<Attribute, Object> attributeMap = new HashMap<>();
        attributeMap.put(Attribute.TOTAL_AMOUNT, couponApplyRequestDto.getTotalAmount());
        attributeMap.put(Attribute.PURCHASE_DATE, LocalDate.now());
        attributeMap.put(Attribute.CATEGORIES, getProductCategories(couponApplyRequestDto));
        return attributeMap;
    }

    private String[] getProductCategories(CouponApplyRequestDto couponApplyRequestDto) {
        List<Long> productIds = couponApplyRequestDto.getCartItems().stream().map(CartItemDTO::getProductId).toList();
        List<Product> products = productRepository.findByIds(productIds);
        if (products.size() != productIds.size()) {
            throw new BadRequestException("Invalid cart items");
        }
        return products.stream().map(Product::getCategory).map(Category::getName).toArray(String[]::new);
    }

}
