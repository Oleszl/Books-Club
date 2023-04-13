package com.excellentbook.excellentbook.dto.user;

import com.excellentbook.excellentbook.dto.address.AddressDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDtoRequest {
    @NotBlank
    @JsonProperty("first_name")
    private String firstName;
    @NotBlank
    @JsonProperty("last_name")
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 3)
    private String password;
    @NotBlank
    @JsonProperty("phone_number")
    private String phoneNumber;
    @NotNull
    private AddressDto address;

}
