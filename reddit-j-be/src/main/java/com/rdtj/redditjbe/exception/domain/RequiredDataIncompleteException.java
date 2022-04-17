package com.rdtj.redditjbe.exception.domain;

public class RequiredDataIncompleteException extends Exception{
    public RequiredDataIncompleteException(String message) {
        super(message);
    }
}
