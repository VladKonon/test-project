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

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key}")
    String secretKey;

    final static String TOKEN_PREFIX = "bearer ";
    final static String HEADER_STRING = "Authorization";
    final static String SCOPE_REFRESH_TOKEN = "refresh_token";

    @Autowired
    @Qualifier("userServiceImpl")
    UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createAccessToken(String username, long validityInMilliseconds) {
        Claims claims = Jwts.claims().setSubject(username);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken(String username, long validityInMilliseconds) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("scope", SCOPE_REFRESH_TOKEN);
        Date now = new Date();
        Date validity = new Date((now.getTime()) + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_STRING);

        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }

        return null;
    }

    public boolean validateAccessToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

        if (claimsJws.getBody().getExpiration().before(new Date())) {
            return false;
        }

            return true;
    }

    public boolean validateRefreshToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

        String scope = claimsJws.getBody().get("scope", String.class);
        System.out.println(scope);

        if (claimsJws.getBody().getExpiration().before(new Date()) || scope == null) {
            return false;
        }

        if (scope.equals(SCOPE_REFRESH_TOKEN)) {
            return true;
        }

        return false;
    }
}
