package com.excellentbook.excellentbook.exception;

import lombok.Getter;

@Getter
public class WebClientGenericException extends RuntimeException {

    public WebClientGenericException(String message) {
        super(message);
    }

}
