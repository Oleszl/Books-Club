package com.excellentbook.excellentbook;

import com.excellentbook.excellentbook.entity.Book;
import com.excellentbook.excellentbook.entity.Category;
import com.excellentbook.excellentbook.entity.Tag;
import com.excellentbook.excellentbook.entity.User;
import com.excellentbook.excellentbook.enums.BookStatus;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModelUtils {

    public static Book getBook() {
        Set<User> buyers = new HashSet<>();
        buyers.add(getUser());
        return Book.builder()
                .id(1L)
                .name("test")
                .authorName("test")
                .description("test")
                .status(BookStatus.AVAILABLE.name().toLowerCase())
                .owner(User.builder()
                        .id(2L)
                        .build())
                .buyers(buyers)
                .build();
    }

    public static List<Category> getCategories() {
        Category category1 = Category.builder()
                .id(1L)
                .name("Category1")
                .build();

        Category category2 = Category.builder()
                .id(2L)
                .name("Category2")
                .build();
        return Arrays.asList(category1, category2);
    }

    public static User getUser() {
        return User.builder()
                .id(1L)
                .email("test@gmail.com")
                .build();
    }

    public static List<Tag> getTags() {
        Tag tag1 = Tag.builder()
                .name("tag1")
                .build();
        Tag tag2 = Tag.builder()
                .name("tag2")
                .build();

        return List.of(tag1, tag2);
    }

}
