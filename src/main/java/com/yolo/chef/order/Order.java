package com.yolo.chef.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

@Entity(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long price;
    private String code;

    @Column(name = "customer_contact_number")
    private String customerContactNumber;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "order_status_id")
    private Integer orderStatusId;
    @Column(name = "address_id")
    private Integer addressId;
}
