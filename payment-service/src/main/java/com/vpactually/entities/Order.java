package com.vpactually.entities;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private Integer id;
    private List<Integer> productIds;
    private Integer userId;
    private String address;
    private Integer price;
    private OrderStatus status;
}
