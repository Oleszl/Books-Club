package com.excellentbook.excellentbook.dto.book;

import com.excellentbook.excellentbook.dto.tag.TagIdDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class BookDtoRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @JsonProperty("author_id")
    private Long authorId;
    @JsonProperty("category_id")
    private Long categoryId;
    private Set<TagIdDto> tags;
    @JsonProperty("user_id")
    private Long userId;
//    @Valid
//    @NotEmpty
//    private Set<User> users;
}
