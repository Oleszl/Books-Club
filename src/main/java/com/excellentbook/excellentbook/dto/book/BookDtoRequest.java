package com.excellentbook.excellentbook.dto.book;

import com.excellentbook.excellentbook.entity.Tag;
import com.excellentbook.excellentbook.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class BookDtoRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @JsonProperty("author_id")
    private String authorId;
    @JsonProperty("category_id")
    private String categoryId;
//    private String photoUrl;
    private Set<Tag> tags;
    @JsonProperty("user_id")
    private User userId;
    @Valid
    @NotEmpty
    private Set<User> users;
}
