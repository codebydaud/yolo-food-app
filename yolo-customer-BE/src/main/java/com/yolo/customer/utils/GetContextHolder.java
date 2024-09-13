package com.yolo.customer.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

public class GetContextHolder {
    public static String getUsernameFromAuthentication(Authentication authentication) {
        if (authentication == null) {
            throw new IllegalArgumentException("Authentication cannot be null");
        }
        if (authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return jwt.getClaimAsString("preferred_username");
        } else {
            throw new IllegalArgumentException("Expected Jwt as principal");
        }
    }
}
