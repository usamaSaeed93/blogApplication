package com.springBoot.blogApplication.exception;

public class PostCreationException extends RuntimeException {

    public PostCreationException(String message) {
        super(message);
    }
}