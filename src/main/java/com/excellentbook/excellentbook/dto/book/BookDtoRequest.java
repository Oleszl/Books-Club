package com.excellentbook.excellentbook.dto.book;

import com.excellentbook.excellentbook.entity.Author;
import com.excellentbook.excellentbook.entity.Category;
import com.excellentbook.excellentbook.entity.Tag;
import com.excellentbook.excellentbook.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    private String photoUrl;
    private Set<Tag> tags;
    @Valid
    @NotEmpty
    private Set<User> users;
}
