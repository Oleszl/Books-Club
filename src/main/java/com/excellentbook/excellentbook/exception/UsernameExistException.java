package com.excellentbook.excellentbook.exception;

public class UsernameExistException extends RuntimeException {

    private String usernameValue;

    public UsernameExistException(String usernameValue) {
        super(String.format("Username: '%s' already exists in the system", usernameValue));
        this.usernameValue = usernameValue;
    }

    public String getUsernameValue() {
        return usernameValue;
    }

    public void setUsernameValue(String usernameValue) {
        this.usernameValue = usernameValue;
    }
}
