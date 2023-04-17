package com.excellentbook.excellentbook.service.impl;

import com.excellentbook.excellentbook.dto.address.AddressDto;
import com.excellentbook.excellentbook.dto.user.UserBookDetailsDto;
import com.excellentbook.excellentbook.dto.user.UserDetailsDto;
import com.excellentbook.excellentbook.dto.user.UserDtoResponse;
import com.excellentbook.excellentbook.entity.Address;
import com.excellentbook.excellentbook.entity.Book;
import com.excellentbook.excellentbook.entity.User;
import com.excellentbook.excellentbook.exception.EmailExistException;
import com.excellentbook.excellentbook.exception.InvalidImageException;
import com.excellentbook.excellentbook.exception.ResourceNotFoundException;
import com.excellentbook.excellentbook.exception.UserNotFoundException;
import com.excellentbook.excellentbook.repository.UserRepository;
import com.excellentbook.excellentbook.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper mapper;

    private final S3BucketStorageServiceImpl s3BucketStorageService;

    private final PasswordEncoder passwordEncoder;

    @Value("${application.bucket.name}")
    private String bucketName;
    @Value("${cloud.aws.region.static}")
    private String s3RegionName;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder,
                           S3BucketStorageServiceImpl s3BucketStorageService) {
        this.userRepository = userRepository;
        this.mapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.s3BucketStorageService = s3BucketStorageService;
    }


    @Override
    public UserDtoResponse getUser() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UserNotFoundException("User", "email", principal.getName()));

        log.info("Found user with id: {}", user.getId());
        return mapper.map(user, UserDtoResponse.class);
    }

    @Override
    public UserDtoResponse updateUserById(Long id, UserDetailsDto userDtoRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if (userRepository.existsByEmail(userDtoRequest.getEmail()) &&
            !Objects.equals(user.getEmail(), userDtoRequest.getEmail())) {
            throw new EmailExistException(userDtoRequest.getEmail());
        }

        user.setFirstName(userDtoRequest.getFirstName());
        user.setLastName(userDtoRequest.getLastName());
        user.setEmail(userDtoRequest.getEmail());
        user.setPhoneNumber(userDtoRequest.getPhoneNumber());

        if (userDtoRequest.getPassword() != null)
            user.setPassword(passwordEncoder.encode(userDtoRequest.getPassword()));

        log.info("User with id: {} was updated", user.getId());
        return mapper.map(userRepository.save(user), UserDtoResponse.class);
    }

    @Override
    public UserDtoResponse updateUserPhoto(Long id, MultipartFile file) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if (file.isEmpty()) {
            log.error("Failed to upload empty file");
            throw new InvalidImageException("Image is not provided");
        }

        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                IMAGE_BMP.getMimeType(),
                IMAGE_GIF.getMimeType(),
                IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
            log.error("FIle uploaded is not an image, provided file format: {}", file.getContentType());
            throw new InvalidImageException("File uploaded is not an image");
        }

        String path = String.format("%s/%s", "user-avatars", UUID.randomUUID());
        s3BucketStorageService.uploadFile(path, file);
        String fullPath = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, s3RegionName, path);
        user.setPhotoUrl(fullPath);

        log.info("Image was added to user with id: {}", user.getId());
        return mapper.map(userRepository.save(user), UserDtoResponse.class);
    }

    @Override
    public UserDtoResponse updateUserAddress(Long id, AddressDto addressDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        Address address = mapper.map(addressDto, Address.class);
        user.setAddress(address);

        log.info("Address details was updated for user with id: {}", user.getId());
        return mapper.map(user, UserDtoResponse.class);
    }

    @Override
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.deleteById(id);
        log.info("User with id: {} was deleted", user.getId());
    }

    @Override
    public List<UserBookDetailsDto> getUserBooksByStatus(Long id, String status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        Set<Book> userBooks = user.getDesiredBooks();
        List<Book> filteredUserBooks = userBooks.stream()
                .filter(b -> b.getStatus().equals(status))
                .toList();

        log.info("List of all desirable books was formed for user with id: {}", user.getId());
        return filteredUserBooks.stream()
                .map(b -> mapper.map(b, UserBookDetailsDto.class))
                .toList();
    }

    @Override
    public List<UserBookDetailsDto> getPersonalUserBooksByStatus(Long id, String status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        List<Book> userBooks = user.getOwnBooks();
        List<Book> filteredUserBooks = userBooks.stream()
                .filter(b -> b.getStatus().equals(status))
                .toList();

        log.info("List of all personal books was formed for user with id: {}", user.getId());
        return filteredUserBooks.stream()
                .map(b -> mapper.map(b, UserBookDetailsDto.class))
                .toList();
    }


}
