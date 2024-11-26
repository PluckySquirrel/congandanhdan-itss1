package vn.edu.hust.soict.japango.common.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtils {
    private final JwtDecoder jwtDecoder;

    public String getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            return jwtAuth.getToken().getTokenValue();
        }
        return null;
    }

    public Jwt decodeToken(String token) {
        if (token == null) return null;
        return jwtDecoder.decode(token);
    }

    public Long getUserId() {
        Jwt jwt = decodeToken(getToken());
        if (jwt == null) return null;
        return jwt.getClaim("id");
    }
}
