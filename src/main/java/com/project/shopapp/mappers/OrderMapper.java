package com.project.shopapp.mappers;

import com.project.shopapp.dtos.requests.OrderRequestDto;
import com.project.shopapp.dtos.responses.OrderResponseDto;
import com.project.shopapp.enums.OrderStatus;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.User;
import com.project.shopapp.services.FileService;
import com.project.shopapp.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ObjectMapperUtils objectMapperUtils;

    public Order mapToEntity(OrderRequestDto orderRequestDto, User user, LocalDate shippingDate) {
        Order order = objectMapperUtils.mapToEntityOrDto(orderRequestDto, Order.class);
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setStatus(OrderStatus.PENDING);
        order.setShippingDate(shippingDate);
        return order;
    }

    public OrderResponseDto mapToDto(Order savedOrder) {
        var orderResponse = objectMapperUtils.mapToEntityOrDto(savedOrder, OrderResponseDto.class);
        orderResponse.getOrderDetails().forEach(order -> order.getProduct().setThumbnail(FileService.getImageUrl(order.getProduct().getThumbnail())));
        return orderResponse;
    }

    public List<OrderResponseDto> mapToDtoList(List<Order> orders) {
        return orders.stream().map(this::mapToDto).collect(Collectors.toList());
    }
}
