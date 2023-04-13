package com.excellentbook.excellentbook.dto.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressDto {
    @NotBlank
    @JsonProperty("build_number")
    private String buildNumber;
    @NotBlank

    private String city;
    @NotBlank

    private String region;
    @NotBlank

    private String street;
    @NotBlank
    @JsonProperty("zip_code")
    private String zipCode;
}
