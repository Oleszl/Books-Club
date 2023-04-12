package com.excellentbook.excellentbook.dto.book;

import com.excellentbook.excellentbook.entity.Author;
import com.excellentbook.excellentbook.entity.Category;
import com.excellentbook.excellentbook.entity.Tag;
import com.excellentbook.excellentbook.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
public class BookDtoRequest {
    private String name;
    private String description;
    private Author author_id;
    private Category category_id;
    private String photoUrl;
    private Set<Tag> tags;
    private Set<User> users;
}
