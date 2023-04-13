package com.excellentbook.excellentbook.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginDto {

    @NotBlank
    private String email ;
    @NotBlank
    @Size(min = 3)
    private String password ;
}