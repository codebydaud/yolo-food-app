package com.yolo.chef.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserProfileRequest {
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String house;
    private String street;
    private String area;
    private String zipCode;
    private String city;
    private String country;
    private String currencyCode;


}
