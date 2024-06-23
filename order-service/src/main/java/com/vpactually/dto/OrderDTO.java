package com.vpactually.dto;

import com.vpactually.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDTO {
    private String id;
    private String address;
    private String price;
    private String status;

    public OrderDTO(Order order) {
        this.id = order.getId().toString();
        this.address = order.getAddress();
        this.price = order.getPrice().toString();
        this.status = order.getStatus().name();
    }
}
