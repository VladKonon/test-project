package com.osdb.test.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotFoundException extends BusinessException {

    public NotFoundException(String message) {
        super(message);
        log.warn(message);
    }
}
