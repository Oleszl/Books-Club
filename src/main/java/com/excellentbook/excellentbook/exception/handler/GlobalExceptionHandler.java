package com.excellentbook.excellentbook.exception.handler;

import com.excellentbook.excellentbook.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleRoleNotFoundException(RoleNotFoundException exception) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<ErrorDetails> handleEmailExistException(EmailExistException exception) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleUsernameNotFoundException(UserNotFoundException exception) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidImageException.class)
    public ResponseEntity<ErrorDetails> handleInvalidImageException(InvalidImageException exception) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AmazonS3UploadException.class)
    public ResponseEntity<ErrorDetails> handleAWSGenericException(AmazonS3UploadException exception) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidBuyerException.class)
    public ResponseEntity<ErrorDetails> handleInvalidBuyerException(InvalidBuyerException exception) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WebClientGenericException.class)
    public final ResponseEntity<ErrorDetails> handleWebClientGenericException(WebClientGenericException e) {
        ErrorDetails exceptionResponse = new ErrorDetails(new Date(), e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler(TimeoutException.class)
    public final ResponseEntity<ErrorDetails> handleTimeoutException(TimeoutException e) {
        ErrorDetails exceptionResponse = new ErrorDetails(new Date(), e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FAILED_DEPENDENCY);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
