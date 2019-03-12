package com.osdb.test.controller.error;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.osdb.test.controller.response.ErrorResponse;
import com.osdb.test.exception.BusinessException;
import com.osdb.test.exception.JwtTokenValidationException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(MethodArgumentNotValidException exception) {
        return ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(exception.getBindingResult().getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .findFirst().orElse(""))
                .date(Instant.now())
                .build();
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(InvalidFormatException exception) {
        return ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(exception.getOriginalMessage())
                .date(Instant.now())
                .build();
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(DataIntegrityViolationException exception) {
        return ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .date(Instant.now())
                .build();
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(IllegalStateException ex) {
        return ErrorResponse.builder()
                .date(Instant.now())
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handle(SignatureException ex) {
        return ErrorResponse.builder()
                .date(Instant.now())
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handle(ExpiredJwtException ex) {
        return ErrorResponse.builder()
                .date(Instant.now())
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handle(AuthenticationException ex) {
        return ErrorResponse.builder()
                .date(Instant.now())
                .message(ex.getMessage() + " Invalid username/password supplied")
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(IllegalArgumentException ex) {
        return ErrorResponse.builder()
                .date(Instant.now())
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(MailException ex) {
        return ErrorResponse.builder()
                .date(Instant.now())
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handle(JwtTokenValidationException ex) {
        return ErrorResponse.builder()
                .date(Instant.now())
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBusinessException(BusinessException ex) {
        return ErrorResponse.builder()
                .date(Instant.now())
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }
}
