package com.example.userservice.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResponse {
    private String productId;
    private String stock;
    private String unitPrice;
    private String totalPrice;
    private LocalDateTime createdAt;

    private String orderId;
}
