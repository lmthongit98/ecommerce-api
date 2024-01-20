package com.project.shopapp.services;

import com.project.shopapp.dtos.requests.OrderRequestDto;
import com.project.shopapp.dtos.requests.PaymentRequestDto;
import com.project.shopapp.dtos.responses.OrderResponseDto;
import com.project.shopapp.dtos.responses.PagingResponseDto;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

    List<OrderResponseDto> getOrdersByUserId(Long userId);

    OrderResponseDto getOrderById(Long orderId);

    void deleteOrder(Long id);

    OrderResponseDto updateOrder(OrderRequestDto orderRequestDto, Long id);

    PagingResponseDto<OrderResponseDto> getOrdersByKeywords(String searchKey, int pageNo, int pageSize, String sortBy, String sortDir);

    PaymentIntent createPaymentIntent(PaymentRequestDto paymentRequestDto) throws StripeException;
}
