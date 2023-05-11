package com.excellentbook.excellentbook.service;

import com.excellentbook.excellentbook.ModelUtils;
import com.excellentbook.excellentbook.dto.book.BookDtoResponse;
import com.excellentbook.excellentbook.dto.book.BookPageableDto;
import com.excellentbook.excellentbook.entity.Book;
import com.excellentbook.excellentbook.entity.User;
import com.excellentbook.excellentbook.exception.InvalidImageException;
import com.excellentbook.excellentbook.exception.ResourceNotFoundException;
import com.excellentbook.excellentbook.repository.BookRepository;
import com.excellentbook.excellentbook.repository.UserRepository;
import com.excellentbook.excellentbook.service.impl.BookServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
    @Mock
    private RequestAttributes attrs;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        RequestContextHolder.setRequestAttributes(attrs);

        // do you when's on attrs
    }

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
        Long id = 2L;
        MultipartFile file = new MockMultipartFile("file", "file.png", "image/png", new byte[]{1, 2, 3});
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.addBookImage(id, file)).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Book not found with id: 2");
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

    @Test
    public void getAllBooksTest() {
        int pageNumber = 0;
        int pageSize = 10;
        String searchValue = "book";
        List<Book> books = new ArrayList<>();
        books.add(Book.builder()
                .id(1L)
                .name("test book")
                .authorName("test author")
                .description("description")
                .build());
        Page<Book> bookPage = new PageImpl<>(books, PageRequest.of(pageNumber, pageSize), books.size());
        when(bookRepository.findBooksByStatusAndNameIgnoreCaseContaining(any(String.class), any(PageRequest.class), any(String.class)))
                .thenReturn(bookPage);

        BookPageableDto bookPageableDto = bookService.getAllBooks(pageNumber, pageSize, searchValue);

        assertEquals(pageNumber, bookPageableDto.getPageNumber());
        assertEquals(pageSize, bookPageableDto.getPageSize());
        assertEquals(books.size(), bookPageableDto.getTotalElements());
        assertEquals(1, bookPageableDto.getTotalPages());
        assertEquals(buildUrlAddress(1, pageSize), bookPageableDto.getNext());
        assertNull(bookPageableDto.getPrev());
        assertEquals(10, bookPageableDto.getContent().size());
    }

    private String buildUrlAddress(int bookNumber, int pageSize) {
        final String endpointPath = "/books";
        final String queryPageNumber = "pageNumber";
        final String queryPageSize = "pageSize";
        final String basePath = "/api/v1";


        return ServletUriComponentsBuilder.fromCurrentContextPath().path(basePath + endpointPath)
                .queryParam(queryPageNumber, bookNumber)
                .queryParam(queryPageSize, pageSize)
                .toUriString();
    }

}

