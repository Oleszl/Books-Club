package com.excellentbook.excellentbook.dto.delivery.external;

import lombok.Data;

import java.util.List;

@Data
public class NovaPoshtaDtoResponse {
    private Boolean success;
    private List<NovaPoshtaDataDto> data;
}
