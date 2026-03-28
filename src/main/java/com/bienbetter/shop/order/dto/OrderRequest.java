package com.bienbetter.shop.order.dto;

import java.time.LocalDateTime;

public record OrderRequest(
        String orderId,
        String userId,
        String itemName,
        int amount,
        LocalDateTime orderDate
) {}