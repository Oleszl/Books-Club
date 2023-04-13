package com.excellentbook.excellentbook.dto.tag;

import com.excellentbook.excellentbook.entity.Book;
import jakarta.validation.Valid;

import java.util.Set;

public class TagDto {
    private String name;
    private Set<Book> books;
}
