package com.excellentbook.excellentbook.dto.book;

import com.excellentbook.excellentbook.entity.Author;
import com.excellentbook.excellentbook.entity.Category;
import com.excellentbook.excellentbook.entity.Tag;
import com.excellentbook.excellentbook.entity.User;
import jakarta.persistence.*;

import java.util.Set;

public class BookDtoResponse {
    private Long id;
    private String name;
    private String description;
    private Author author;
    private Category category;
    private String photoUrl;
    private String status;
    private Set<Tag> tags;
    private Set<User> users;
}
