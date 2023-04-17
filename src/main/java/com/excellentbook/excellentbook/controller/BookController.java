package com.excellentbook.excellentbook.controller;

import com.excellentbook.excellentbook.constant.AppConstants;
import com.excellentbook.excellentbook.dto.book.BookDtoRequest;
import com.excellentbook.excellentbook.dto.book.BookDtoResponse;
import com.excellentbook.excellentbook.dto.book.BookOrderDetailsDto;
import com.excellentbook.excellentbook.dto.book.BookPageableDto;
import com.excellentbook.excellentbook.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public BookDtoResponse saveBook(@Valid @RequestBody BookDtoRequest bookDtoRequest) {
        return bookService.saveBook(bookDtoRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
    }

    @GetMapping("/{id}")
    public BookDtoResponse getBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PatchMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDtoResponse addBookImage(@PathVariable("id") Long bookId, @ModelAttribute("image") MultipartFile image) {
        return bookService.addBookImage(bookId, image);
    }

    @GetMapping
    public BookPageableDto getAllBooks(@RequestParam(value = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
                                       @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                       @RequestParam(value = "searchType", required = false) String searchType,
                                       @RequestParam(value = "searchValue", required = false) String searchValue) {

        return bookService.getAllBooks(pageNumber, pageSize, searchType, searchValue);
    }

    @PostMapping("/{bookId}/users/{userId}")
    public BookDtoResponse addBookToUser(@PathVariable Long bookId, @PathVariable Long userId) {
        return bookService.addBookToUser(userId, bookId);
    }

    @PostMapping("/{bookId}/users/{userId}/approve")
    public BookOrderDetailsDto approveBookForUser(@PathVariable Long bookId, @PathVariable Long userId) {
        return bookService.approveBookForParticularUser(userId, bookId);
    }
}
