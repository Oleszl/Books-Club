package com.excellentbook.excellentbook.exception;

public class EmailExistException extends RuntimeException {

    private String emailValue;

    public EmailExistException(String emailValue) {
        super(String.format("Email: '%s' already exists in the system", emailValue));
        this.emailValue = emailValue;
    }

    public String getEmailValue() {
        return emailValue;
    }

    public void setEmailValue(String fieldValue) {
        this.emailValue = emailValue;
    }
}
