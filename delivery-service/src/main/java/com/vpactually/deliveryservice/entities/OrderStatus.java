package com.vpactually.deliveryservice.entities;

public enum OrderStatus {
    CREATED,
    PAYMENT_SUCCESS,
    PAYMENT_FAILED,
    RESTAURANT_SUCCESS,
    RESTAURANT_FAILED,
    DELIVERY_SUCCESS,
    DELIVERY_IN_PROCESS,
    DELIVERY_FAILED
}
