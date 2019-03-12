package com.osdb.test.exception;

public class JwtTokenValidationException extends RuntimeException {

    public JwtTokenValidationException(String message) {
        super(message);
    }
}
