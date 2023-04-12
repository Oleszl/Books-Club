package com.excellentbook.excellentbook.service;

import com.excellentbook.excellentbook.dto.book.BookDtoResponse;

public interface BookService {
    BookDtoResponse getAllBooks(int pageNumber, int pageSize, String sortBy, String sortDir);
    BookDtoResponse getBookById();
    BookDtoResponse saveBook();
    BookDtoResponse updateBookById(Long id);
    BookDtoResponse deleteBookById(Long id);
}
