package com.excellentbook.excellentbook.controller;

import com.excellentbook.excellentbook.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {
    private static final String bookLink = "/api/v1/books";
    private MockMvc mockMvc;

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .build();
    }

    @Test
    void getBookByIdTest() throws Exception {
        Long bookId = 1L;
        mockMvc.perform(get(bookLink + "/" + bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bookService).getBookById(1L);
    }

    @Test
    void getAllBooksTest() throws Exception {
        int pageNumber = 0;
        int pageSize = 10;
        String searchValue = "test";
        mockMvc.perform(get(bookLink)
                        .queryParam("pageNumber", "0")
                        .queryParam("pageSize", "10")
                        .queryParam("searchType", "name")
                        .queryParam("searchValue", "test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bookService).getAllBooks(pageNumber, pageSize, searchValue);
    }

    @Test
    void deleteBookByIdTest() throws Exception {
        Long bookId = 1L;
        mockMvc.perform(delete(bookLink + "/" + bookId))
                .andExpect(status().isNoContent());
        verify(bookService).deleteBookById(bookId);
    }

}
