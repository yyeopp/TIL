package com.reactivespring.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReviewsServerException extends RuntimeException{

    private String message;

    public ReviewsServerException(String message) {
        super(message);
        log.info("####################################################");
        this.message = message;
    }
}
