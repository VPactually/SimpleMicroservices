package com.vpactually.messaging.kafka.publishers;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publishOrderStatusFromPaymentService(String message) {
        kafkaTemplate.send("order_status_from_payment_service", message);
    }

}
