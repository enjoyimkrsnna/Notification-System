package com.krishna.notificationsystem.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.naming.ServiceUnavailableException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<?> handleNotificationNotFoundException(NotificationNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotificationException.class)
    public ResponseEntity<?> handleNotificationException(NotificationException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleJsonParseException(HttpMessageNotReadableException ex, WebRequest request) {
        String errorMessage = "Invalid value provided for NotificationType or NotificationChannel.";
        if (ex.getMessage().contains("NotificationType")) {
            errorMessage = "Incorrect NotificationType provided. Accepted values are: OTP, TRANSACTIONAL, PROMOTIONAL , SYSTEM, REMINDER";
        } else if(ex.getMessage().contains("NotificationChannel")) {
            errorMessage = "Incorrect NotificationChannel provided. Accepted values are: EMAIL , SMS, PUSH .";
        }
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>("Validation failed: " + errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        return new ResponseEntity<>("Database error: Data integrity violation.", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity<?> handleTimeoutException(TimeoutException ex, WebRequest request) {
        return new ResponseEntity<>("The request timed out. Please try again later.", HttpStatus.GATEWAY_TIMEOUT);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<?> handleNumberFormatException(NumberFormatException ex, WebRequest request) {
        return new ResponseEntity<>("Invalid number format provided. Please check your input.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<?> handleServiceUnavailableException(ServiceUnavailableException ex, WebRequest request) {
        return new ResponseEntity<>("The service is currently unavailable. Please try again later.", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(NotificationRateLimitExceededException.class)
    public ResponseEntity<?> handleNotificationRateLimitExceededException(NotificationRateLimitExceededException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
