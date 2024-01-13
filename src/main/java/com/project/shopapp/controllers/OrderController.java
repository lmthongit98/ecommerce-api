package com.project.shopapp.controllers;

import com.project.shopapp.dtos.requests.OrderRequestDto;
import com.project.shopapp.dtos.responses.OrderResponseDto;
import com.project.shopapp.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderRequestDto orderRequestDto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        OrderResponseDto orderResponseDto = orderService.createOrder(orderRequestDto);
        return ResponseEntity.ok(orderResponseDto);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOrders(@PathVariable("user_id") Long userId) {
        return ResponseEntity.ok("ok");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody @Valid OrderRequestDto orderRequestDto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return ResponseEntity.ok("created order successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {

        return ResponseEntity.ok("Deleted order successfully!");
    }

}
