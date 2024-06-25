package com.vpactually.deliveryservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Integer id;
    private List<Integer> productIds;
    private Integer userId;
    private String address;
    private Integer price;
    private OrderStatus status;
}
