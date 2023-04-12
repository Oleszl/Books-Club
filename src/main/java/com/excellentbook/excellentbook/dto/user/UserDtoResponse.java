package com.excellentbook.excellentbook.dto.user;

import com.excellentbook.excellentbook.dto.address.AddressDto;
import com.excellentbook.excellentbook.entity.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDtoResponse {
    private Long id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String email;
    @JsonProperty("photo_url")
    private String photoUrl;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private AddressDto address;
    private String active;

}
