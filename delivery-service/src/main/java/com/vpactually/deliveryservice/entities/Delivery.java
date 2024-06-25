package com.vpactually.deliveryservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "delivery")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private String address;
    private OrderStatus status;

    public Delivery(Order order) {
        this.userId = order.getUserId();
        this.address = order.getAddress();
        this.status =  order.getStatus();
    }
}
