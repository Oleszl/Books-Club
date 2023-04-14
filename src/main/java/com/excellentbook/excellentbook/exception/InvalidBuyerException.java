package com.excellentbook.excellentbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidBuyerException extends RuntimeException{
    public InvalidBuyerException(String message) {
        super(message);
    }
}
