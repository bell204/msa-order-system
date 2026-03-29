package com.bienbetter.order.entity;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Builder
@Data
public class Order {
    private Long id;
    private String orderId;
    private String userId;
    private String itemName;
    private long amount;
    private LocalDateTime orderDate;
}