package com.excellentbook.excellentbook.dto.auth;

import lombok.Data;

@Data
public class SignUpDtoRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}