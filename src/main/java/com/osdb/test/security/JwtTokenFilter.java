package com.osdb.test.security;

import io.jsonwebtoken.JwtException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JwtTokenFilter extends GenericFilterBean {

    JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);

        try {
            if (token != null && jwtTokenProvider.validateAccessToken(token)) {
                Authentication authentication = token != null ? jwtTokenProvider.getAuthentication(token) : null;
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(servletRequest, servletResponse);

        } catch (JwtException ex) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
