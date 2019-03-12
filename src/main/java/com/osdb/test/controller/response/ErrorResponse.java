package com.osdb.test.controller.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse {

    Instant date;

    String message;

    int code;
}
