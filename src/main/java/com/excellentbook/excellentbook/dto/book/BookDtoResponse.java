package com.excellentbook.excellentbook.dto.book;

import com.excellentbook.excellentbook.dto.category.CategoryDto;
import com.excellentbook.excellentbook.dto.tag.TagDto;
import com.excellentbook.excellentbook.dto.user.UserDto;
import com.excellentbook.excellentbook.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDtoResponse {
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
    private UserDto owner;
    private Set<UserDto> buyers;
}
