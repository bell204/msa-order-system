package com.bienbetter.order.service;

import com.bienbetter.common.event.OrderCreatedEvent;
import com.bienbetter.order.entity.Order;
import com.bienbetter.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionSynchronization;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderProducer orderProducer;

    @Transactional
    public String processOrder(OrderCreatedEvent request) {
        String generatedOrderId = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        // 1. DB 저장을 위한 Entity 생성
        Order orderEntity = Order.builder()
                .orderId(generatedOrderId)
                .userId(request.getUserId())
                .itemName(request.getItemName())
                .amount(request.getAmount())
                .orderDate(now)
                .build();

        // 2. DB 저장 실행
        orderMapper.insertOrder(orderEntity);
        log.info("DB 저장 완료: {}", generatedOrderId);

        // 3. 카프카 전송을 위한 DTO 재구성 (발행 시간 포함)
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(generatedOrderId)
                .userId(request.getUserId())
                .itemName(request.getItemName())
                .amount(request.getAmount())
                .createdAt(now)
                .build();

        // 4. 트랜잭션 커밋 완료 후 카프카 발행 (중요!)
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    orderProducer.sendOrder(event);
                }
            });
        } else {
            // 트랜잭션이 없는 환경에서도 동작해야 한다면 바로 발송
            orderProducer.sendOrder(event);
        }

        return generatedOrderId;
    }
}