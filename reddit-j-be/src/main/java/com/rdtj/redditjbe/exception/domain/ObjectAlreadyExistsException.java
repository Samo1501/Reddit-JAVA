package com.rdtj.redditjbe.exception.domain;

public class ObjectAlreadyExistsException extends Exception{
    public ObjectAlreadyExistsException(String message) {
        super(message);
    }
}
