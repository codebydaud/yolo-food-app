package com.yolo.chef.user;

import com.yolo.chef.dto.CreateUserRequest;
import com.yolo.chef.dto.UserInfoResponse;
import com.yolo.chef.exception.InvalidException;
import com.yolo.chef.exception.InvalidJwtException;
import com.yolo.chef.exception.UserAlreadyExistsException;
import com.yolo.chef.exception.UserCreationException;
import com.yolo.chef.userProfile.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.http.HttpStatus;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;


    public UserInfoResponse getUserInfoAndCreateUser(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();

        String username = jwt.getClaim("preferred_username");
        if (username == null) {
            throw new InvalidJwtException("JWT claim missing", "Missing 'preferred_username' claim in JWT");
        }

        String email = jwt.getClaim("email");
        String name = jwt.getClaim("name");

        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess == null) {
            throw new InvalidJwtException("JWT claim missing", "Missing 'resource_access' claim in JWT");
        }

        Map<String, Object> yoloChefAccess = (Map<String, Object>) resourceAccess.get("yolo-chef");
        List<String> roles = Collections.emptyList();  // Default to empty list

        if (yoloChefAccess != null) {
            roles = (List<String>) yoloChefAccess.get("roles");
            if (roles == null) {
                roles = Collections.emptyList();  // Ensure roles is not null
            }
        }

        String userCreationMessage = createUserIfNotExists(username, email);
        return new UserInfoResponse(username, email, name, roles, userCreationMessage);
    }


    public String createUserIfNotExists(String username, String email) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            return "User already exists";
        }

        CreateUserRequest createUserRequest = new CreateUserRequest(username, email);
        createUser(createUserRequest);
        return "User created successfully";
    }

    public ResponseEntity<Map<String, String>> createUser(CreateUserRequest createUserRequest) {
        if (createUserRequest.username() == null || createUserRequest.email() == null) {
            throw new InvalidException("Invalid user data", "Username or email is null");
        }

        Optional<User> existingUser = userRepository.findByUsername(createUserRequest.username());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User already exists", "Username: " + createUserRequest.username());
        }

        try {
            User user = new User();
            user.setUsername(createUserRequest.username());
            user.setEmail(createUserRequest.email());
            user.setRoleId(1);
            user.setIsDeleted(false);
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());

            userRepository.save(user);

            Map<String, String> response = new HashMap<>();
            response.put("message", "User created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (DataIntegrityViolationException e) {
            throw new UserCreationException("Error creating user", "Data integrity violation: " + e.getMessage());
        }
    }

    public boolean checkUserProfileExists(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username must not be null or empty");
        }

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            long userId = userOptional.get().getId();
            return userProfileRepository.existsByUserId(userId);
        }

        return false;
    }

}