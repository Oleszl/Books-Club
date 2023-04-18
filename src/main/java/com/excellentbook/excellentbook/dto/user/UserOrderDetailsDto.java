package com.excellentbook.excellentbook.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserOrderDetailsDto {
    private Long id;
    private String name;
    private String description;
    @JsonProperty("author_name")
    private String authorName;
    @JsonProperty("photo_url")
    private String photoUrl;
    private String status;
    private UserDto buyer;
    private UserDto owner;
}
