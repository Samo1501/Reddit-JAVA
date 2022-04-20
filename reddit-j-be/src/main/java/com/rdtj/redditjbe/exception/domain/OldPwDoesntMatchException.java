package com.rdtj.redditjbe.exception.domain;

public class OldPwDoesntMatchException extends Exception{
    public OldPwDoesntMatchException(String message) {
        super(message);
    }
}
