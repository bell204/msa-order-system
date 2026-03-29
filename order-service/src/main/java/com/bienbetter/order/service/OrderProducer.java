package com.bienbetter.shop.order.service;

import com.bienbetter.shop.order.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, OrderRequest> kafkaTemplate;
    private static final String TOPIC = "order-topic";

    public void sendOrder(OrderRequest order) {
        log.info("카프카 메시지 발행 시작: {}", order.orderId());

        kafkaTemplate.send(TOPIC, order.orderId(), order)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("메시지 전송 성공: topic={}, offset={}",
                                result.getRecordMetadata().topic(),
                                result.getRecordMetadata().offset());
                    } else {
                        log.error("메시지 전송 실패: {}", ex.getMessage());
                        // 추가 로직: 실패 시 별도 DB에 로깅하거나 재시도 큐에 넣는 로직이 들어갈 수 있음
                    }
                });
    }
}