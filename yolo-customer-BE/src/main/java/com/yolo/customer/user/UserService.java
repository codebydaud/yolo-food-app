package com.yolo.customer.user;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Constructor injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Map<String, Object> extractUserDetails(Jwt jwt) {
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("name", jwt.getClaim("name"));
        userDetails.put("username", jwt.getClaim("preferred_username"));
        userDetails.put("email", jwt.getClaim("email"));

        List<String> roles = jwt.getClaimAsStringList("roles");
        if (roles == null || roles.isEmpty()) {
            roles = extractRolesFromJwt(jwt);
        }
        userDetails.put("roles", roles);

        if (!userRepository.existsByUsername(jwt.getClaim("preferred_username"))) {
            createUser(jwt.getClaim("preferred_username"), jwt.getClaim("email"));
        }
        return userDetails;
    }


    public User createUser(String username, String email) {

        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required.");
        }

        if (userRepository.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists.");
        }

        if (userRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists.");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setIsDeleted(false);

        return userRepository.save(newUser);
    }

    private List<String> extractRolesFromJwt(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaimAsMap("resource_access");
        Map<String, Object> clientRoles = (Map<String, Object>) resourceAccess.get("Yolo-Customer");

        if (clientRoles == null || clientRoles.get("roles") == null) {
            return Collections.emptyList();
        }

        List<String> roles = (List<String>) clientRoles.get("roles");

        return roles.stream()
                .map(role -> role.replace(" ", "_"))
                .collect(Collectors.toList());
    }

}

