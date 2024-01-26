package com.project.shopapp.controllers;

import com.project.shopapp.constants.AppConstants;
import com.project.shopapp.dtos.requests.OrderRequestDto;
import com.project.shopapp.dtos.requests.PaymentRequestDto;
import com.project.shopapp.dtos.responses.OrderResponseDto;
import com.project.shopapp.dtos.responses.PagingResponseDto;
import com.project.shopapp.services.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentRequestDto paymentRequestDto) throws StripeException {
        log.info("paymentInfo.amount: " + paymentRequestDto.getAmount());
        PaymentIntent intent = orderService.createPaymentIntent(paymentRequestDto);
        String paymentIntent = intent.toJson();
        return ResponseEntity.ok(paymentIntent);
    }

    @GetMapping
    public ResponseEntity<?> getOrdersByKeyword(
            @RequestParam(value = "search_key", defaultValue = AppConstants.EMPTY, required = false) String searchKey,
            @RequestParam(value = "page_no", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "page_size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sort_by", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sort_dir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        PagingResponseDto<OrderResponseDto> pagingResponseDto = orderService.getOrdersByKeywords(searchKey, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(pagingResponseDto);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        OrderResponseDto orderResponseDto = orderService.createOrder(orderRequestDto);
        //todo: trigger event to send mail to customer
        return ResponseEntity.ok(orderResponseDto);
    }

    @GetMapping("user/{user_id}")
    public ResponseEntity<?> getOrders(@PathVariable("user_id") Long userId) {
        List<OrderResponseDto> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable("id") Long orderId) {
        OrderResponseDto orderResponseDto = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody @Valid OrderRequestDto orderRequestDto) {
        OrderResponseDto orderResponseDto = orderService.updateOrder(orderRequestDto, id);
        return ResponseEntity.ok(orderResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
