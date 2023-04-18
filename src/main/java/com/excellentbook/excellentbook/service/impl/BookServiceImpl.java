package com.excellentbook.excellentbook.service.impl;

import com.excellentbook.excellentbook.dto.book.BookDtoRequest;
import com.excellentbook.excellentbook.dto.book.BookDtoResponse;
import com.excellentbook.excellentbook.dto.book.BookOrderDetailsDto;
import com.excellentbook.excellentbook.dto.book.BookPageableDto;
import com.excellentbook.excellentbook.dto.tag.TagIdDto;
import com.excellentbook.excellentbook.dto.user.UserDto;
import com.excellentbook.excellentbook.entity.*;
import com.excellentbook.excellentbook.enums.BookStatus;
import com.excellentbook.excellentbook.exception.InvalidBuyerException;
import com.excellentbook.excellentbook.exception.InvalidImageException;
import com.excellentbook.excellentbook.exception.ResourceNotFoundException;
import com.excellentbook.excellentbook.exception.UnavailableBookException;
import com.excellentbook.excellentbook.repository.BookRepository;
import com.excellentbook.excellentbook.repository.CategoryRepository;
import com.excellentbook.excellentbook.repository.TagRepository;
import com.excellentbook.excellentbook.repository.UserRepository;
import com.excellentbook.excellentbook.service.BookService;
import com.excellentbook.excellentbook.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final S3BucketStorageServiceImpl s3BucketStorageService;
    private final OrderService orderService;
    private final ModelMapper mapper;
    @Value("${application.bucket.name}")
    private String bucketName;
    @Value("${cloud.aws.region.static}")
    private String s3RegionName;
    @Value("${app.base-path}")
    private String basePath;

    public BookServiceImpl(BookRepository bookRepository, UserRepository userRepository,
                           CategoryRepository categoryRepository, TagRepository tagRepository,
                           S3BucketStorageServiceImpl s3BucketStorageService,
                           ModelMapper mapper, OrderService orderService) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.s3BucketStorageService = s3BucketStorageService;
        this.mapper = mapper;
        this.orderService = orderService;
    }

    @Override
    public BookPageableDto getAllBooks(int pageNumber, int pageSize, String searchValue) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        if (searchValue == null) {
            searchValue = "";
        }

        Page<Book> books = bookRepository.findBooksByStatusAndNameIgnoreCaseContaining(
                BookStatus.AVAILABLE.name().toLowerCase(), pageable, searchValue);

        List<BookDtoResponse> booklist = books.getContent().stream()
                .map(content -> mapper.map(content, BookDtoResponse.class))
                .toList();

        return formPaginationInfo(books, booklist, pageSize);
    }

    private BookPageableDto formPaginationInfo(Page<Book> books, List<BookDtoResponse> booklist, int pageSize) {
        final String endpointPath = "/books";
        final String queryPageNumber = "pageNumber";
        final String queryPageSize = "pageSize";

        BookPageableDto bookPageableDto = new BookPageableDto();

        if (books.getNumber() == 0) {
            bookPageableDto.setPrev(null);
            if (books.getTotalElements() > pageSize) {
                int bookNumber = books.getNumber() + 1;
                bookPageableDto.setNext(ServletUriComponentsBuilder.fromCurrentContextPath().path(basePath + endpointPath)
                        .queryParam(queryPageNumber, bookNumber)
                        .queryParam(queryPageSize, pageSize)
                        .toUriString());
            } else {
                bookPageableDto.setNext(null);
            }
        } else if (books.isLast()) {
            int bookNumber = books.getNumber() - 1;
            bookPageableDto.setPrev(ServletUriComponentsBuilder.fromCurrentContextPath().path(basePath + endpointPath)
                    .queryParam(queryPageNumber, bookNumber)
                    .queryParam(queryPageSize, pageSize)
                    .toUriString());
            bookPageableDto.setNext(null);
        } else {
            int prevBookNumber = books.getNumber() - 1;
            int nextBookNumber = books.getNumber() + 1;
            bookPageableDto.setPrev(ServletUriComponentsBuilder.fromCurrentContextPath().path(basePath + endpointPath)
                    .queryParam(queryPageNumber, prevBookNumber)
                    .queryParam(queryPageSize, pageSize)
                    .toUriString());
            bookPageableDto.setNext(ServletUriComponentsBuilder.fromCurrentContextPath().path(basePath + endpointPath)
                    .queryParam(queryPageNumber, nextBookNumber)
                    .queryParam(queryPageSize, pageSize)
                    .toUriString());
        }

        bookPageableDto.setContent(booklist);
        bookPageableDto.setPageNumber(books.getNumber());
        bookPageableDto.setPageSize(books.getSize());
        bookPageableDto.setTotalElements(books.getTotalElements());
        bookPageableDto.setTotalPages(books.getTotalPages());

        log.info("List of books was formed, total elements: {}", books.getTotalElements());
        return bookPageableDto;
    }

    @Override
    public BookDtoResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
        log.info("Found book with id: {}", book.getId());
        return mapper.map(book, BookDtoResponse.class);
    }

    @Override
    public BookDtoResponse saveBook(BookDtoRequest bookDtoRequest) {
        Book book = mapper.map(bookDtoRequest, Book.class);
        User user = userRepository.findById(bookDtoRequest.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", bookDtoRequest.getOwnerId()));

        Category category = categoryRepository.findById(bookDtoRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", bookDtoRequest.getCategoryId()));

        Set<Tag> tags = new HashSet<>();
        for (TagIdDto item : bookDtoRequest.getTags()) {
            Tag tag = tagRepository.findById(item.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", item.getId()));
            tags.add(tag);
        }
        book.setOwner(user);
        book.setCategory(category);
        book.setTags(tags);
        book.setStatus(BookStatus.AVAILABLE.name().toLowerCase());

        log.info("Book with id: {} was saved", book.getId());
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
        log.info("Book with id: {} was deleted", book.getId());
    }

    @Override
    public BookDtoResponse addBookImage(Long id, MultipartFile file) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));

        if (file.isEmpty()) {
            log.error("Failed to upload empty file");
            throw new InvalidImageException("Cannot upload empty file");
        }

        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                IMAGE_BMP.getMimeType(),
                IMAGE_GIF.getMimeType(),
                IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
            log.error("FIle uploaded is not an image, provided file format: {}", file.getContentType());
            throw new InvalidImageException("Uploaded fIle is not an image");
        }

        String path = String.format("%s/%s", "user-books", UUID.randomUUID());
        s3BucketStorageService.uploadFile(path, file);
        String fullPath = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, s3RegionName, path);
        book.setPhotoUrl(fullPath);

        log.info("Image was added to book with id: {}", book.getId());
        return mapper.map(bookRepository.save(book), BookDtoResponse.class);
    }

    @Override
    public BookDtoResponse addBookToUser(Long userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));
        if (!(book.getStatus().equals(BookStatus.AVAILABLE.name().toLowerCase()))) {
            throw new UnavailableBookException(bookId);
        } else {
            if (book.getOwner().getId().equals(userId)) {
                throw new InvalidBuyerException("Buyer cannot claim its own book");
            }
            book.getBuyers().add(user);
            bookRepository.save(book);
        }

        log.info("Book with id: {} was added to user with id: {}", book.getId(), user.getId());
        return mapper.map(book, BookDtoResponse.class);
    }

    @Override
    public BookOrderDetailsDto approveBookForParticularUser(Long userId, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Order order;
        if ((book.getStatus().equals(BookStatus.UNAVAILABLE.name().toLowerCase()))) {
            throw new UnavailableBookException(bookId);
        } else {
            order = orderService.addDetailsToOrder(book, user);
            book.setStatus(BookStatus.UNAVAILABLE.name().toLowerCase());
            bookRepository.save(book);
        }
        UserDto owner = mapper.map(order.getOwner(), UserDto.class);
        UserDto buyer = mapper.map(order.getOwner(), UserDto.class);

        BookOrderDetailsDto response = new BookOrderDetailsDto();
        response.setId(order.getId());
        response.setBookId(order.getBook().getId());
        response.setOwner(owner);
        response.setBuyer(buyer);

        return response;
    }
}
