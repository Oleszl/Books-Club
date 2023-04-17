package com.excellentbook.excellentbook.dto.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressDto {
    @NotBlank(message = "Build number cannot be empty")
    @JsonProperty("build_number")
    private String buildNumber;
    @NotBlank(message = "City cannot be empty")
    @Pattern(regexp = "^[A-Za-z]+$", message = "City must consist of letters only")
    private String city;
    @NotBlank(message = "Region cannot be empty")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Region must consist of letters only")
    private String region;
    @NotBlank(message = "Street cannot be empty")
    private String street;
    @NotBlank(message = "Zip code cannot be empty")
    @Size(min = 3, max = 15, message = "Zip code must be longer than 3 and less than 15 characters")
    @JsonProperty("zip_code")
    private String zipCode;
}
