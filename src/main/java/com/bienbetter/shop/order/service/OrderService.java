package com.bienbetter.shop.order.service;

import com.bienbetter.shop.order.dto.OrderRequest;
import com.bienbetter.shop.order.entitiy.Order;
import com.bienbetter.shop.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderProducer orderProducer;

    @Transactional
    public String processOrder(OrderRequest request) {
        String generatedOrderId = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        // 1. DB 저장을 위한 Entity 생성
        Order orderEntity = Order.builder()
                .orderId(generatedOrderId)
                .userId(request.userId())
                .itemName(request.itemName())
                .amount(request.amount())
                .orderDate(now)
                .build();

        // 2. DB 저장 실행
        orderMapper.insertOrder(orderEntity);
        log.info("DB 저장 완료: {}", generatedOrderId);

        // 3. 카프카 전송을 위한 DTO 재구성 (발행 시간 포함)
        OrderRequest orderEvent = new OrderRequest(
                generatedOrderId,
                request.userId(),
                request.itemName(),
                request.amount(),
                now
        );

        // 4. 카프카 발행
        orderProducer.sendOrder(orderEvent);

        return generatedOrderId;
    }
}