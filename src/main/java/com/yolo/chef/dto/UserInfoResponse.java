package com.yolo.chef.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoResponse {
    private String username;
    private String email;
    private String name;
    private List<String> roles;
    private String message;

}
