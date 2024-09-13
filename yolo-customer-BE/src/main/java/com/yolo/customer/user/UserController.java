package com.yolo.customer.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ROLE_DECODE_USER_INFO', 'ROLE_CREATE_ACCOUNT')")
    @GetMapping("/jwtToken")
    public Map<String, Object> decodeJwt(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        Map<String, Object> userDetails = userService.extractUserDetails(jwt);
        Map<String, Object> response = new HashMap<>();

        // Check if roles are present
        List<String> roles = (List<String>) userDetails.get("roles");
        if (roles == null || roles.isEmpty()) {
            response.put("error", "User does not have any roles assigned");
        } else {
            response.put("user_details", userDetails);
        }

        return response;
    }

    @PreAuthorize("hasRole('ROLE_CREATE_ACCOUNT')")
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest.getUsername(), userRequest.getEmail());
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }


}
