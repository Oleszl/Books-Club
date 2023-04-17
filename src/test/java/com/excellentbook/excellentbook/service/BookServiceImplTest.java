package com.excellentbook.excellentbook.service;

import com.excellentbook.excellentbook.ModelUtils;
import com.excellentbook.excellentbook.dto.book.BookDtoResponse;
import com.excellentbook.excellentbook.entity.Book;
import com.excellentbook.excellentbook.entity.User;
import com.excellentbook.excellentbook.exception.InvalidImageException;
import com.excellentbook.excellentbook.exception.ResourceNotFoundException;
import com.excellentbook.excellentbook.repository.BookRepository;
import com.excellentbook.excellentbook.repository.UserRepository;
import com.excellentbook.excellentbook.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    private final Book book = ModelUtils.getBook();
    private final User user = ModelUtils.getUser();

    @Test
    void getBookByIdTest() {
        BookDtoResponse expected = BookDtoResponse.builder()
                .id(1L)
                .name("test")
                .authorName("test")
                .status("available")
                .build();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(modelMapper.map(any(), eq(BookDtoResponse.class)))
                .thenReturn(expected);

        assertEquals(expected.getName(), bookService.getBookById(1L).getName());
    }

    @Test
    void deleteBookByIdTest() {
        Long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        bookService.deleteBookById(id);
        verify(bookRepository).deleteById(id);
    }

    @Test
    void addBookToUserTest() {
        Long userId = 1L;
        Long bookId = 1L;

        BookDtoResponse expectedResponse = BookDtoResponse.builder()
                .id(book.getId())
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookService.addBookToUser(userId, bookId)).thenReturn(expectedResponse);
        BookDtoResponse actualResponse = bookService.addBookToUser(userId, bookId);

        assertEquals(expectedResponse.getId(), actualResponse.getId());
    }

    @Test
    void addBookImageThrowResourceNotFoundExceptionTest() {
        Long id = 1L;
        MultipartFile file = new MockMultipartFile("file", "file.png", "image/png", new byte[]{1, 2, 3});
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.addBookImage(id, file)).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found with id: 1");
    }

    @Test
    void addBookImageThrowInvalidImageExceptionTest() {
        Long id = 1L;
        MultipartFile file = new MockMultipartFile("file", "file.png", "image/png", new byte[]{});
        Book book = ModelUtils.getBook();
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        assertThatThrownBy(() -> bookService.addBookImage(id, file)).isInstanceOf(InvalidImageException.class)
                .hasMessage("Cannot upload empty file");
    }

}
