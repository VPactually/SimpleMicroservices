package com.vpactually.services;

import com.vpactually.entities.Delivery;
import com.vpactually.entities.Order;
import com.vpactually.repositories.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.vpactually.enums.OrderStatus.*;

@Service
@RequiredArgsConstructor
public class DeliveryDomainService {

    private final DeliveryRepository repository;

    public void process(Order order) {
        order.setStatus(DELIVERY_IN_PROCESS);
        var delivery = new Delivery(order);
        repository.save(delivery);
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        order.setStatus(DELIVERY_SUCCESS);
        delivery.setStatus(DELIVERY_SUCCESS);
        repository.save(delivery);
    }

    public void rollback(Order backupOrder) {
        backupOrder.setStatus(DELIVERY_FAILED);
        var delivery = new Delivery(backupOrder);
        repository.save(delivery);
    }
}
