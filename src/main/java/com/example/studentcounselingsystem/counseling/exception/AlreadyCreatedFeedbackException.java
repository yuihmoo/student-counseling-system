package com.example.studentcounselingsystem.counseling.exception;

public class AlreadyCreatedFeedbackException extends RuntimeException {
    private final String message;

    public AlreadyCreatedFeedbackException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
