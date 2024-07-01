package com.vpactually.services.impl;

import com.vpactually.entities.Delivery;
import com.vpactually.entities.Order;
import com.vpactually.repositories.DeliveryRepository;
import com.vpactually.services.DeliveryDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.vpactually.enums.OrderStatus.*;

@Service
@RequiredArgsConstructor
public class DeliveryDomainServiceImpl implements DeliveryDomainService {

    private final DeliveryRepository repository;

    public void process(Order order) {
        deliveryResult(order);
        if (order.getStatus().equals(DELIVERY_SUCCESS)) {
            return;
        }
        var delivery = new Delivery(order);
        repository.save(delivery);
    }

    public void deliveryResult(Order order) {
        order.setStatus(order.getStatus().equals(RESTAURANT_SUCCESS) ?
                DELIVERY_IN_PROCESS : DELIVERY_SUCCESS);
    }
    public void rollback(Order backupOrder) {
        backupOrder.setStatus(DELIVERY_FAILED);
        var delivery = new Delivery(backupOrder);
        repository.save(delivery);
    }
}
