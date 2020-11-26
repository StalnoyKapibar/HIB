package com.project.exceptions;

//Custom Exception on authorize failure
public class NoValuePresentException extends RuntimeException {
    public NoValuePresentException(String s) {
        super(s);
    }
}
