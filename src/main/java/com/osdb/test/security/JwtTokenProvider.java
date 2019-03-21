package com.osdb.test.security;

import io.jsonwebtoken.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key}")
    String secretKey;

    private final static String TOKEN_PREFIX = "bearer ";
    private final static String HEADER_STRING = "Authorization";
    private final static String SCOPE_REFRESH_TOKEN = "refresh_token";

    @Autowired
    @Qualifier("userServiceImpl")
    UserDetailsService userDetailsService;

    public String createAccessToken(String username, long validityInMilliseconds) {
        Claims claims = Jwts.claims().setSubject(username);
        return createToken(claims, validityInMilliseconds);
    }

    public String createRefreshToken(String username, long validityInMilliseconds) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("scope", SCOPE_REFRESH_TOKEN);

        return createToken(claims, validityInMilliseconds);
    }

    private String createToken(Claims claims, Long validityInMilliseconds) {
        Instant currentDate = Instant.now();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(currentDate))
                .setExpiration(Date.from(currentDate.plusMillis(validityInMilliseconds)))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_STRING);

        if (Objects.nonNull(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }

        return null;
    }

    public boolean validateAccessToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return !claimsJws.getBody().getExpiration().before(new Date());
    }

    public boolean validateRefreshToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        String scope = claimsJws.getBody().get("scope", String.class);

        if (claimsJws.getBody().getExpiration().before(new Date()) || scope == null) {
            return false;
        }

        return scope.equals(SCOPE_REFRESH_TOKEN);
    }
}
