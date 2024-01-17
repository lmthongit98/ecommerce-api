package com.project.shopapp.services;

import com.project.shopapp.dtos.requests.OrderRequestDto;
import com.project.shopapp.dtos.responses.OrderResponseDto;

import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

    List<OrderResponseDto> findByUserId(Long userId);

    OrderResponseDto getOrderById(Long orderId);

    void deleteOrder(Long id);

    OrderResponseDto updateOrder(OrderRequestDto orderRequestDto, Long id);
}
