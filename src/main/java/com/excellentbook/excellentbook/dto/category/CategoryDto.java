package com.excellentbook.excellentbook.dto.category;

import com.excellentbook.excellentbook.entity.Book;
import jakarta.validation.Valid;

import java.util.Set;

public class CategoryDto {
    private Long id;
    private String name;
    private Set<Book> books;
}