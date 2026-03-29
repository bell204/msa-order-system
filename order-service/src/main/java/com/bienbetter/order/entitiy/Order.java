package com.bienbetter.shop.order.entitiy;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Order(
        Long id,
        String orderId,
        String userId,
        String itemName,
        int amount,
        LocalDateTime orderDate
) {}