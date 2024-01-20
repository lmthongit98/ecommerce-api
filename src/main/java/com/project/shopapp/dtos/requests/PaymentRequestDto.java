package com.project.shopapp.dtos.requests;

import lombok.Data;

@Data
public class PaymentRequestDto {
    private int amount;
    private String currency;
    private String receiptEmail;
}
