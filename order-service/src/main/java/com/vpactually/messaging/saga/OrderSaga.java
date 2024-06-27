package com.vpactually.messaging.saga;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpactually.entities.Order;
import com.vpactually.messaging.publishers.KafkaPublisher;
import com.vpactually.services.OrderDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.vpactually.enums.OrderStatus.DELIVERY_IN_PROCESS;

@Component
@RequiredArgsConstructor
public class OrderSaga {

    private final OrderDomainService service;
    private final KafkaPublisher publisher;
    private final ObjectMapper om;

    public void doSaga(Order order) {
        switch (order.getStatus()) {
            case CREATED:
                sagaNewOrder(order);
                break;
            case PAYMENT_SUCCESS:
                order.setStatus(DELIVERY_IN_PROCESS);
                sagaPaidOrder(order);
                break;
            case RESTAURANT_SUCCESS:
                sagaConfirmedOrder(order);
                break;
            case DELIVERY_SUCCESS:
                break;
            default:
                onFailure(service.getBackupOrder());
                break;
        }
    }

    public void sagaNewOrder(Order order) {
        try {
            publisher.publishOrderCreated(om.writeValueAsString(order));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sagaPaidOrder(Order order) {
        try {
            publisher.publishOrderPaid(om.writeValueAsString(order));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sagaConfirmedOrder(Order order) {
        try {
            publisher.publishConfirmedOrder(om.writeValueAsString(order));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void onFailure(Order order) {
        try {
            publisher.publishFailedOrder(om.writeValueAsString(order));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
