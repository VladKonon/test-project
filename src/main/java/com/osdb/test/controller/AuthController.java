package com.osdb.test.controller;

import com.osdb.test.controller.dto.SignInDto;
import com.osdb.test.controller.dto.SignUpDto;
import com.osdb.test.controller.facade.AuthFacade;
import io.swagger.annotations.Api;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(tags = "auth-api", description = "REST API endpoints for Authentication")
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/api/v1/public/auth")
public class AuthController {

    AuthFacade authFacade;

    @PostMapping("/sign-up")
    public ResponseEntity signUp(@Valid @RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(authFacade.signUp(signUpDto));
    }

    @PostMapping("/sign-in")
    public ResponseEntity signIn(@RequestBody SignInDto signInDto) {
        return ResponseEntity.ok(authFacade.signIn(signInDto));
    }

    @GetMapping("/validate-token")
    public ResponseEntity validateToken(HttpServletRequest httpServletRequest) {
        authFacade.validateToken(httpServletRequest);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/refresh-token")
    public ResponseEntity refreshTokens(String refreshToken) {
        return ResponseEntity.ok(authFacade.RefreshToken(refreshToken));
    }

    @PatchMapping("/reset-password")
    @ResponseBody
    public ResponseEntity resetPassword(String email) {
        authFacade.resetPassword(email);

        return ResponseEntity.ok().build();
    }
}
