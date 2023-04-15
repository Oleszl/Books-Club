package com.excellentbook.excellentbook.dto.delivery.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NovaPoshtaPropertiesDto {
    @JsonProperty("CityName")
    private String cityName;
    @JsonProperty("Page")
    private String page;
    @JsonProperty("Limit")
    private String limit;
    @JsonProperty("Language")
    private final String language = "UA";
}
