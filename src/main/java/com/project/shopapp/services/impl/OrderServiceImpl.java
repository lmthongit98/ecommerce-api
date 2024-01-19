package com.project.shopapp.services.impl;

import com.project.shopapp.dtos.requests.CartItemDTO;
import com.project.shopapp.dtos.requests.OrderRequestDto;
import com.project.shopapp.dtos.responses.OrderResponseDto;
import com.project.shopapp.dtos.responses.PagingResponseDto;
import com.project.shopapp.exceptions.ResourceNotFoundException;
import com.project.shopapp.mappers.OrderMapper;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderDetail;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.services.OrderService;
import com.project.shopapp.utils.PageableUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private final OrderMapper orderMapper;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        User user = userRepository.findById(orderRequestDto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User", orderRequestDto.getUserId()));
        LocalDate shippingDate = orderRequestDto.getShippingDate() == null ? LocalDate.now() : orderRequestDto.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Date must be at least today !");
        }
        Order order = orderMapper.mapToEntity(orderRequestDto, user, shippingDate);
        List<OrderDetail> orderDetails = getOrderDetails(orderRequestDto, order);
        order.setOrderDetails(orderDetails);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.mapToDto(savedOrder);
    }

    @Override
    public List<OrderResponseDto> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orderMapper.mapToDtoList(orders);
    }

    @Override
    public OrderResponseDto getOrderById(Long orderId) {
        Order order = getOrder(orderId);
        return orderMapper.mapToDto(order);
    }

    private Order getOrder(Long orderId) {
        return orderRepository.findByIdAndActive(orderId, true).orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = getOrder(id);
        order.setActive(false);
        orderRepository.save(order);
    }

    @Override
    public OrderResponseDto updateOrder(OrderRequestDto orderRequestDto, Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cannot find order with id: " + id));
        User existingUser = userRepository.findById(orderRequestDto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("Cannot find user with id: " + id));
        orderMapper.mapToEntity(order, orderRequestDto, existingUser);
        return orderMapper.mapToDto(orderRepository.save(order));
    }

    @Override
    public PagingResponseDto<OrderResponseDto> getOrdersByKeywords(String searchKey, int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable pageable = PageableUtils.getPageable(pageNo, pageSize, sortBy, sortDir);
        Page<Order> orders = orderRepository.findByKeyword(searchKey, pageable);
        List<Order> listOfOrders = orders.getContent();
        List<OrderResponseDto> content = orderMapper.mapToDtoList(listOfOrders);
        return new PagingResponseDto<>(orders, content);
    }

    private List<OrderDetail> getOrderDetails(OrderRequestDto orderRequestDto, Order order) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItemDTO cartItemDTO : orderRequestDto.getCartItems()) {
            Product product = productRepository.findById(cartItemDTO.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product", cartItemDTO.getProductId()));
            OrderDetail orderDetail = OrderDetail.builder()
                    .product(product)
                    .order(order)
                    .numberOfProducts(cartItemDTO.getQuantity())
                    .price(product.getPrice())
                    .build();
            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }

}
