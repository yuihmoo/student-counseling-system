package com.example.studentcounselingsystem.counseling.exception;

public class AlreadyUpdatedFeedbackException extends RuntimeException {
    private final String message;

    public AlreadyUpdatedFeedbackException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
