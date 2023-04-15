package com.excellentbook.excellentbook.dto.delivery;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PostOfficeDataDto {
    private String description;
    @JsonProperty("city_ref")
    private String сityRef;
    @JsonProperty("postal_code")
    private String postalCodeUA;
}
