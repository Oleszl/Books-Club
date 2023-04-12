package com.excellentbook.excellentbook.service.impl;

import com.excellentbook.excellentbook.dto.book.BookDtoResponse;
import com.excellentbook.excellentbook.repository.BookRepository;
import com.excellentbook.excellentbook.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ModelMapper mapper;

    public BookServiceImpl(BookRepository bookRepository, ModelMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    @Override
    public BookDtoResponse getAllBooks(int pageNumber, int pageSize, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public BookDtoResponse getBookById() {
        return null;
    }

    @Override
    public BookDtoResponse saveBook() {
        return null;
    }

    @Override
    public BookDtoResponse updateBookById(Long id) {
        return null;
    }

    @Override
    public BookDtoResponse deleteBookById(Long id) {
        return null;
    }
}
