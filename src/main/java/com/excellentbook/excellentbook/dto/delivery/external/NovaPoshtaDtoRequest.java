package com.excellentbook.excellentbook.dto.delivery.external;

import lombok.Data;

@Data
public class NovaPoshtaDtoRequest {
    private String apiKey;
    private final String modelName = "Address";
    private final String calledMethod = "getWarehouses";
    private NovaPoshtaPropertiesDto methodProperties;

}
