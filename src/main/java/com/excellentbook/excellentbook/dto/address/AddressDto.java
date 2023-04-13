package com.excellentbook.excellentbook.dto.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddressDto {
    @JsonProperty("build_number")
    private String buildNumber;
    private String city;
    private String region;
    private String street;
    @JsonProperty("zip_code")
    private String zipCode;
}
