package com.excellentbook.excellentbook.dto.delivery.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NovaPoshtaDataDto {
    @JsonProperty("Description")
    private String description;
    @JsonProperty("CityRef")
    private String сityRef;
    @JsonProperty("PostalCodeUA")
    private String postalCodeUA;
}
