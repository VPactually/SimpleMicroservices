package com.vpactually.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpactually.dto.CreateOrderDTO;
import com.vpactually.entities.Order;
import com.vpactually.messaging.publishers.KafkaPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCreateCommandHandler {

    private final KafkaPublisher publisher;
    private final ObjectMapper om;


    public Order handle(CreateOrderDTO createOrderDTO) {
        return new Order(createOrderDTO);
    }


}
