package com.yolo.chef.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class SecurityContextPrinter {

    public static void printSecurityContext() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication != null) {
            System.out.println("Authentication: " + authentication);

            if (authentication instanceof JwtAuthenticationToken) {
                JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
                Jwt jwt = (Jwt) jwtAuth.getPrincipal();

                // Extract specific claims from the Jwt
                String username = jwt.getClaim("preferred_username"); // Adjust based on your JWT structure
                String email = jwt.getClaim("email"); // Adjust based on your JWT structure

                System.out.println("Principal (username): " + (username != null ? username : "Not available"));
                System.out.println("Principal (email): " + (email != null ? email : "Not available"));
            } else {
                System.out.println("Principal: " + authentication.getPrincipal());
            }

            System.out.println("Authorities: ");
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                System.out.println(" - " + authority.getAuthority());
            }
        } else {
            System.out.println("No authentication information found.");
        }
    }
}
