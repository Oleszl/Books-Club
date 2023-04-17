package com.excellentbook.excellentbook.dto.order;

import com.excellentbook.excellentbook.dto.user.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderDetailsDto {
    private Long id;
    @JsonProperty("book_id")
    private Long bookId;

    private UserDto owner;

    private UserDto buyer;
}
