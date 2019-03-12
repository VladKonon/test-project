package com.osdb.test.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlreadyExistsException extends BusinessException {

    public AlreadyExistsException(String message) {
        super(message);
        log.warn(message);
    }
}
