package com.excellentbook.excellentbook.dto.auth;

import lombok.Data;

@Data
public class SignUpDtoResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String photoUrl;
    private String active;
}
