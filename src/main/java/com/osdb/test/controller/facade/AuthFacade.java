package com.osdb.test.controller.facade;

import com.osdb.test.controller.dto.AuthDto;
import com.osdb.test.controller.dto.SignInDto;
import com.osdb.test.controller.dto.SignUpDto;
import com.osdb.test.controller.dto.UserDto;
import com.osdb.test.controller.mapper.SignUpMapper;
import com.osdb.test.controller.mapper.UserMapper;
import com.osdb.test.entity.jpa.User;
import com.osdb.test.exception.JwtTokenValidationException;
import com.osdb.test.security.JwtTokenProvider;
import com.osdb.test.service.impl.UserServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthFacade {

    private static final String EXPIRED_OR_INVALID_JWT_TOKEN = "Expired or invalid JWT token";

    @Value("${security.jwt.token.access.expire-length}")
    private static long validityForAccessToken;

    @Value("${security.jwt.token.refresh.expire-length}")
    private static long validityForRefreshToken;

    AuthenticationManager authenticationManager;
    JwtTokenProvider jwtTokenProvider;
    UserServiceImpl userService;
    UserMapper userMapper;
    SignUpMapper signUpMapper;

    public AuthDto signUp(SignUpDto signUpDto) {
        userService.save(signUpMapper.convertToEntity(signUpDto));

        return authenticate(signUpDto.getEmail(), signUpDto.getPassword());
    }

    public AuthDto signIn(SignInDto signInDto) {
        return authenticate(signInDto.getEmail(), signInDto.getPassword());
    }

    public void validateToken(HttpServletRequest httpServletRequest) {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);

        if (!jwtTokenProvider.validateAccessToken(token)) {
            throw new JwtTokenValidationException(EXPIRED_OR_INVALID_JWT_TOKEN);
        }
    }

    public AuthDto RefreshToken(String refreshToken) {
        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            throw new JwtTokenValidationException(EXPIRED_OR_INVALID_JWT_TOKEN);
        }

        String email = jwtTokenProvider.getUsername(refreshToken);

        return getTokens(email);
    }

    public void resetPassword(String email) {
        userService.resetPassword(email);
    }

    private AuthDto authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        return getTokens(email);
    }

    private AuthDto getTokens(String email) {
        String accessToken = jwtTokenProvider.createAccessToken(email, validityForAccessToken);
        String refreshToken = jwtTokenProvider.createRefreshToken(email, validityForRefreshToken);

        UserDto userDto = userMapper.convertToDto((User) userService.loadUserByUsername(email));

        return AuthDto.builder()
                .user(userDto)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
