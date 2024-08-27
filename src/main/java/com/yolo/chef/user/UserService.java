package com.yolo.chef.user;


import com.yolo.chef.dto.LoginRequest;
import com.yolo.chef.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;
import org.springframework.http.HttpStatus;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;

    public ResponseEntity<Map<String, String>> createUser(LoginRequest loginRequest) {
        validationUtil.validateNewUser(loginRequest);
        User user = new User();
        user.setUsername(loginRequest.username());
        user.setEmail(loginRequest.email());
        user.setRole_id(1);
        user.setIs_deleted(false);
        user.setCreated_at(LocalDateTime.now());
        user.setUpdated_at(LocalDateTime.now());
        userRepository.save(user);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User created successfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
