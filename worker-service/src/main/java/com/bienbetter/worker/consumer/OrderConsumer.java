package com.bienbetter.worker.consumer;
import com.bienbetter.common.event.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OrderConsumer {

    @KafkaListener(topics = "order-topic", groupId = "worker-group")
    public void consume(OrderCreatedEvent event) {
        // 1. 알림 발송 로직 호출 (Email, SMS 등)
        // 2. 외부 시스템 연동 로직 호출
        System.out.println("주문 수신 완료: " + event.getOrderId());
    }
}