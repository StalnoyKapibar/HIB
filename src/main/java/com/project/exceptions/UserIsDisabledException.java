package com.project.exceptions;

public class UserIsDisabledException extends RuntimeException  {
    public UserIsDisabledException(String message) {
        super(message);
    }
}
