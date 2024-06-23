package com.vpactually.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDTO {
    private List<Integer> productIds;
    private Integer userId;
    private String address;
    private Integer price;
}
