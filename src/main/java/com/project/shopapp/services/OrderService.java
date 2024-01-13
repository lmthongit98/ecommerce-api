package com.project.shopapp.services;

import com.project.shopapp.dtos.requests.OrderRequestDto;
import com.project.shopapp.dtos.responses.OrderResponseDto;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);
}
