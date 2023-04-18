package com.excellentbook.excellentbook.service;

import com.excellentbook.excellentbook.dto.book.BookDtoRequest;
import com.excellentbook.excellentbook.dto.book.BookDtoResponse;
import com.excellentbook.excellentbook.dto.book.BookOrderDetailsDto;
import com.excellentbook.excellentbook.dto.book.BookPageableDto;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {
    BookPageableDto getAllBooks(int pageNumber, int pageSize, String searchValue);

    BookDtoResponse getBookById(Long id);

    BookDtoResponse saveBook(BookDtoRequest bookDtoRequest);

    BookDtoResponse updateBookById(Long id, BookDtoRequest bookDtoRequest);

    void deleteBookById(Long id);

    BookDtoResponse addBookImage(Long id, MultipartFile file);

    BookDtoResponse addBookToUser(Long userId, Long bookId);

    BookOrderDetailsDto approveBookForParticularUser(Long userId, Long bookId);
}
