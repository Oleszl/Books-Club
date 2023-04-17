package com.excellentbook.excellentbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomUsernameNotFoundException  extends RuntimeException{
    public CustomUsernameNotFoundException(String message) {
        super(message);
    }
}
