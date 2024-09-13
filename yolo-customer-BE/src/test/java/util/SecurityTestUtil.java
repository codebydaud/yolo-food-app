package util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SecurityTestUtil {

    public static void setJwtAuthenticationToken(String username, Set<String> authorities, Map<String, Object> claims) {
        // Convert Set<String> to Collection<? extends GrantedAuthority>
        Collection<? extends GrantedAuthority> grantedAuthorities = authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        Jwt jwt = Jwt.withTokenValue("mocked-token")
                .header("alg", "RS256")
                .claims(claimsMap -> claimsMap.putAll(claims))
                .claim("preferred_username", username)
                .build();

        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(jwt, grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
    }
}
