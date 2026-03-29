package com.bienbetter.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;              // Add this for @Data
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;   // Add this for LocalDateTime

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {
    private String orderId;
    private String userId;
    private String itemName;
    private Long amount;
    private LocalDateTime createdAt;
}