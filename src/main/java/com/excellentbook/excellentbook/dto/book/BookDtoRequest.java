package com.excellentbook.excellentbook.dto.book;

import com.excellentbook.excellentbook.dto.tag.TagIdDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class BookDtoRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @JsonProperty("author_name")
    private String authorName;
    @JsonProperty("category_id")
    private Long categoryId;
    private Set<TagIdDto> tags;
    @JsonProperty("owner_id")
    private Long ownerId;
}
