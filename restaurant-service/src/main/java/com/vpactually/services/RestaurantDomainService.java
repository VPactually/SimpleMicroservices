package com.vpactually.services;

import com.vpactually.entities.Order;
import com.vpactually.enums.OrderStatus;
import com.vpactually.entities.Product;
import com.vpactually.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantDomainService {

    private final ProductRepository repository;

    public void create(Product product) {
        repository.save(product);
    }

    public void checkOrder(Order order) {
        var restProducts = repository.findAll();
        var restProductIds = restProducts.stream()
                .collect(Collectors.toMap(Product::getId, Product::getQuantity));
        var orderProducts = order.getProductIds().stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        var resultOfCheck = orderProducts.entrySet().stream()
                .allMatch(entry -> restProductIds.containsKey(entry.getKey())
                        && restProductIds.get(entry.getKey()) >= entry.getValue());

        if (resultOfCheck) {
            restProducts.forEach(product -> product.setQuantity(
                    product.getQuantity() - orderProducts.get(product.getId()).intValue()));
            repository.saveAll(restProducts);
            order.setStatus(OrderStatus.RESTAURANT_SUCCESS);
        } else {
            order.setStatus(OrderStatus.RESTAURANT_FAILED);
        }
    }

    public void rollback(Order backupOrder) {
        var productIdsMap = backupOrder.getProductIds().stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        var restProducts = repository.findAll();
        restProducts.forEach(product -> product.setQuantity(
                product.getQuantity() + productIdsMap.get(product.getId()).intValue()));
        repository.saveAll(restProducts);
    }
}
