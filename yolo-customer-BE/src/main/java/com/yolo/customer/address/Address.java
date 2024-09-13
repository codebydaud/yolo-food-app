package com.yolo.customer.address;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "house", nullable = false, length = 16)
    private String house;

    @Column(name = "street", nullable = false, length = 16)
    private String street;

    @Column(name = "area", length = 32)
    private String area;

    @Column(name = "zip_code", length = 5)
    private String zipCode;

    @Column(name = "city", nullable = false, length = 32)
    private String city;

    @Column(name = "country", nullable = false, length = 32)
    private String country;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
