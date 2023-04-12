package com.excellentbook.excellentbook.dto.auth;

import com.excellentbook.excellentbook.dto.address.AddressDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegisterUserDtoRequest {
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String email;
    private String password;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private AddressDto address;
}