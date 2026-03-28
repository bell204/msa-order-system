package com.bienbetter.shop.order.controller;

import com.bienbetter.shop.order.dto.OrderRequest;
import com.bienbetter.shop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders") // 복수형 권장
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService; // Producer를 직접 부르지 않고 Service를 거침

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest request) {
        // 비즈니스 로직(DB 저장 + Kafka 발행)은 서비스에서 처리
        String orderId = orderService.processOrder(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("주문 접수 완료: " + orderId);
    }
}