package com.excellentbook.excellentbook.service.impl;

import com.excellentbook.excellentbook.dto.book.BookDtoRequest;
import com.excellentbook.excellentbook.dto.book.BookDtoResponse;
import com.excellentbook.excellentbook.dto.book.BookPageableDto;
import com.excellentbook.excellentbook.dto.tag.TagIdDto;
import com.excellentbook.excellentbook.entity.*;
import com.excellentbook.excellentbook.enums.BookStatus;
import com.excellentbook.excellentbook.exception.ResourceNotFoundException;
import com.excellentbook.excellentbook.repository.*;
import com.excellentbook.excellentbook.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final S3BucketStorageServiceImpl s3BucketStorageService;
    private final ModelMapper mapper;
    @Value("${application.bucket.name}")
    private String bucketName;
    @Value("${cloud.aws.region.static}")
    private String s3RegionName;

    public BookServiceImpl(BookRepository bookRepository, UserRepository userRepository, AuthorRepository authorRepository
            , CategoryRepository categoryRepository, TagRepository tagRepository, S3BucketStorageServiceImpl s3BucketStorageService, ModelMapper mapper) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.s3BucketStorageService = s3BucketStorageService;
        this.mapper = mapper;
    }

    @Override
    public BookPageableDto getAllBooks(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Book> books = bookRepository.findBooksByStatus(BookStatus.AVAILABLE.name().toLowerCase(), pageable);
        List<BookDtoResponse> booklist = books.getContent().stream()
                .map(content -> mapper.map(content, BookDtoResponse.class))
                .toList();

        BookPageableDto bookPageableDto = new BookPageableDto();
        bookPageableDto.setContent(booklist);
        bookPageableDto.setPageNo(books.getNumber());
        bookPageableDto.setPageSize(books.getSize());
        bookPageableDto.setTotalElements(books.getTotalElements());
        bookPageableDto.setTotalPages(books.getTotalPages());
        bookPageableDto.setLast(books.isLast());


        return bookPageableDto;
    }

    @Override
    public BookDtoResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return mapper.map(book, BookDtoResponse.class);
    }

    @Override
    public BookDtoResponse saveBook(BookDtoRequest bookDtoRequest) {
        Book book = mapper.map(bookDtoRequest, Book.class);
        User user = userRepository.findById(bookDtoRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", bookDtoRequest.getUserId()));

        Category category = categoryRepository.findById(bookDtoRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", bookDtoRequest.getCategoryId()));

        Author author = authorRepository.findById(bookDtoRequest.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", bookDtoRequest.getAuthorId()));

        Set<Tag> tags = new HashSet<>();
        for (TagIdDto item : bookDtoRequest.getTags()) {
            Tag tag = tagRepository.findById(item.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", item.getId()));
            tags.add(tag);
        }
        book.setOwner(user);
        book.setCategory(category);
        book.setAuthor(author);
        book.setTags(tags);
        book.setStatus(BookStatus.AVAILABLE.name().toLowerCase());
        return mapper.map(bookRepository.save(book), BookDtoResponse.class);
    }

    @Override
    public BookDtoResponse updateBookById(Long id, BookDtoRequest bookDtoRequest) {
        return null;
    }

    @Override
    public void deleteBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        book.setOwner(null);
        book.setTags(null);
        bookRepository.deleteById(id);
    }

    @Override
    public BookDtoResponse addBookImage(Long id, MultipartFile file) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }

        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                IMAGE_BMP.getMimeType(),
                IMAGE_GIF.getMimeType(),
                IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("FIle uploaded is not an image");
        }

        String path = String.format("%s/%s", "user-books", UUID.randomUUID());
        s3BucketStorageService.uploadFile(path, file);
        String fullPath = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, s3RegionName, path);
        book.setPhotoUrl(fullPath);
        return mapper.map(bookRepository.save(book), BookDtoResponse.class);
    }
}
