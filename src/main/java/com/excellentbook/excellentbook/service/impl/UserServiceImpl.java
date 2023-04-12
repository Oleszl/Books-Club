package com.excellentbook.excellentbook.service.impl;

import com.excellentbook.excellentbook.dto.auth.RegisterUserDtoRequest;
import com.excellentbook.excellentbook.dto.auth.RegisterUserDtoResponse;
import com.excellentbook.excellentbook.dto.user.UserDtoRequest;
import com.excellentbook.excellentbook.dto.user.UserDtoResponse;
import com.excellentbook.excellentbook.entity.Address;
import com.excellentbook.excellentbook.entity.Role;
import com.excellentbook.excellentbook.entity.User;
import com.excellentbook.excellentbook.exception.EmailExistException;
import com.excellentbook.excellentbook.exception.ResourceNotFoundException;
import com.excellentbook.excellentbook.repository.RoleRepository;
import com.excellentbook.excellentbook.repository.UserRepository;
import com.excellentbook.excellentbook.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.apache.http.entity.ContentType.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final ModelMapper mapper;

    private final S3BucketStorageService s3BucketStorageService;

    private final PasswordEncoder passwordEncoder;

    @Value("${application.bucket.name}")
    private String bucketName;
    @Value("${cloud.aws.region.static}")
    private String s3RegionName;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           ModelMapper modelMapper, PasswordEncoder passwordEncoder,
                           S3BucketStorageService s3BucketStorageService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.s3BucketStorageService = s3BucketStorageService;
    }

    @Override
    public RegisterUserDtoResponse createUser(RegisterUserDtoRequest signUpDto) {

        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new EmailExistException(signUpDto.getEmail());
        }
        User user = new User();
        user.setFirstName(signUpDto.getFirstName());
        user.setLastName(signUpDto.getLastName());
        user.setEmail(signUpDto.getEmail());
        user.setActive(false);
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setPhoneNumber(signUpDto.getPhoneNumber());

        Address address = mapper.map(signUpDto.getAddress(), Address.class);
        user.setAddress(address);

        Role roles = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singleton(roles));

        return mapper.map(userRepository.save(user), RegisterUserDtoResponse.class);
    }

    @Override
    public UserDtoResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return mapper.map(user, UserDtoResponse.class);
    }

    @Override
    public UserDtoResponse updateUserById(Long id, UserDtoRequest userDtoRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.setFirstName(userDtoRequest.getFirstName());
        user.setLastName(userDtoRequest.getLastName());
        user.setEmail(userDtoRequest.getEmail());
        if (userDtoRequest.getPassword() != null)
            user.setPassword(passwordEncoder.encode(userDtoRequest.getPassword()));

        return mapper.map(userRepository.save(user), UserDtoResponse.class);
    }

    @Override
    public UserDtoResponse updateUserPhoto(Long id, MultipartFile file) {
        User user = userRepository.findById(id)
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

        String path = String.format("%s/%s", "user-avatars", UUID.randomUUID());
//        s3BucketStorageService.uploadFile(path, file);
        String fullPath = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, s3RegionName, path);
        user.setPhotoUrl(fullPath);
        return mapper.map(userRepository.save(user), UserDtoResponse.class);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.deleteById(id);
    }
}
