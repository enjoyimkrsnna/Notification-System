package com.krishna.notificationsystem.exception;

public class NotificationRateLimitExceededException extends RuntimeException {
    public NotificationRateLimitExceededException(String message) {
        super(message);
    }
}