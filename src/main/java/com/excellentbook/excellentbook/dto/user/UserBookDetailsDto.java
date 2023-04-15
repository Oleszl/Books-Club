package com.excellentbook.excellentbook.dto.user;

import com.excellentbook.excellentbook.dto.category.CategoryDto;
import com.excellentbook.excellentbook.dto.tag.TagDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;

@Data
public class UserBookDetailsDto {
    private Long id;
    private String name;
    private String description;
    @JsonProperty("author_name")
    private String authorName;
    private CategoryDto category;
    @JsonProperty("photo_url")
    private String photoUrl;
    private String status;
    private Set<TagDto> tags;
}
