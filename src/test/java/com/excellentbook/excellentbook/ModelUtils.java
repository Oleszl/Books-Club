package com.excellentbook.excellentbook;

import com.excellentbook.excellentbook.dto.book.BookDtoRequest;
import com.excellentbook.excellentbook.dto.book.BookDtoResponse;
import com.excellentbook.excellentbook.dto.tag.TagIdDto;
import com.excellentbook.excellentbook.entity.*;
import com.excellentbook.excellentbook.enums.BookStatus;
import org.junit.jupiter.api.Tags;

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
    public static Category getCategory() {
        return Category.builder()
                .name("test")
                .build();
    }

    public static BookDtoRequest getBookDtoRequest() {
        Set<TagIdDto> tags = new HashSet<>();
        tags.add(new TagIdDto(1L));
        return BookDtoRequest.builder()
                .ownerId(1L)
                .categoryId(1L)
                .name("Sample Title")
                .authorName("Sample Author")
                .description("Sample Description")
                .tags(tags)
                .build();
    }
    public static BookDtoResponse getBookDtoResponse(){
        return BookDtoResponse.builder()
                .name("Sample Title")
                .authorName("Sample Author")
                .description("Sample Description")
                .build();
    }

    public static Tag getTag() {
        Tag tag = new Tag();
        tag.setId(1L);

        return tag;
    }
}
