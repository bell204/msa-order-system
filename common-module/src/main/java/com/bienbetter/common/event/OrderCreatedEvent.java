package com.bienbetter.common.event;

import lombok.Builder;
import lombok.Data;              // Add this for @Data
import java.time.LocalDateTime;   // Add this for LocalDateTime

@Builder
@Data
public class OrderCreatedEvent {
    private String orderId;
    private String userId;
    private String itemName;
    private Long amount;
    private LocalDateTime createdAt;
}