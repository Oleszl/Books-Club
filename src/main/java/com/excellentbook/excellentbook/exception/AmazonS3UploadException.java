package com.excellentbook.excellentbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AmazonS3UploadException extends RuntimeException {

    public AmazonS3UploadException(String message) {
        super(message);
    }
}
