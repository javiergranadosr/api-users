package com.apiusers.exception;

public class ErrorDuplicateKey extends RuntimeException{
    public ErrorDuplicateKey(String message) {
        super(message);
    }
}
