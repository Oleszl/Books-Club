package com.excellentbook.excellentbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UnavailableBookException extends RuntimeException{
    public UnavailableBookException(Long id) {
        super(String.format("Book with id: %d is already unavailable", id));
    }
}

