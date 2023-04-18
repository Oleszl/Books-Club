package com.excellentbook.excellentbook.dto.user;

import com.excellentbook.excellentbook.dto.address.AddressDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String email;
    @JsonProperty("phone_Number")
    private String phoneNumber;
    private AddressDto address;
}
