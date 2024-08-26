package com.yolo.chef.address;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String house;
    private String street;
    private String area;
    private String zip_code;
    private String city;
    private String country;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}

