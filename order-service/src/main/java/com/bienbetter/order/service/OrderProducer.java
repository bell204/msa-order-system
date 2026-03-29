package com.bienbetter.order.service;

import com.bienbetter.common.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
    private static final String TOPIC = "order-topic";

    public void sendOrder(OrderCreatedEvent event) {
        log.info("카프카 메시지 발행 시작: {}", event.getOrderId());

        // 메시지 키를 orderId로 설정하여 순서 보장 (필요시)
        kafkaTemplate.send(TOPIC, event.getOrderId(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("메시지 전송 성공: topic={}, offset={}",
                                result.getRecordMetadata().topic(),
                                result.getRecordMetadata().offset());
                    } else {
                        log.error("메시지 전송 실패: orderId={}, error={}",
                                event.getOrderId(), ex.getMessage());
                    }
                });
    }
}