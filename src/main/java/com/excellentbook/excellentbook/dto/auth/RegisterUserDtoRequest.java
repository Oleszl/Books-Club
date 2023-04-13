package com.excellentbook.excellentbook.dto.auth;

import com.excellentbook.excellentbook.dto.address.AddressDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterUserDtoRequest {
    @NotBlank(message = "123")
    @JsonProperty("first_name")
    private String firstName;
    @NotBlank
    @JsonProperty("last_name")
    private String lastName;
    @NotBlank
    private String email;
    private String password;
    @NotBlank
    @JsonProperty("phone_number")
    private String phoneNumber;
    @NotNull
    @Valid
    private AddressDto address;
}