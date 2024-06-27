package com.vpactually.entities;

import com.vpactually.dto.CreateOrderDTO;
import com.vpactually.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ElementCollection
    private List<Integer> productIds;
    private Integer userId;
    private String address;
    private Integer price;
    private OrderStatus status;

    public Order(CreateOrderDTO createOrderDTO) {
        this.productIds = createOrderDTO.getProductIds();
        this.userId = createOrderDTO.getUserId();
        this.address = createOrderDTO.getAddress();
        this.price = createOrderDTO.getPrice();
        this.status = OrderStatus.CREATED;
    }
}
