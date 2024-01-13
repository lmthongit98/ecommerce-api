package com.project.shopapp.mappers;

import com.project.shopapp.dtos.requests.OrderRequestDto;
import com.project.shopapp.dtos.responses.OrderResponseDto;
import com.project.shopapp.enums.OrderStatus;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.User;
import com.project.shopapp.utils.ObjectMapperUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class OrderMapper {

    private ObjectMapperUtils objectMapperUtils;

    public Order mapToEntity(OrderRequestDto orderRequestDto, User user, LocalDate shippingDate) {
        Order order = objectMapperUtils.mapToEntityOrDto(orderRequestDto, Order.class);
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setStatus(OrderStatus.PENDING);
        order.setShippingDate(shippingDate);
        return order;
    }

    public OrderResponseDto mapToDto(Order savedOrder) {
        return objectMapperUtils.mapToEntityOrDto(savedOrder, OrderResponseDto.class);
    }
}
