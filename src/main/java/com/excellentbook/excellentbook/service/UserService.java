package com.excellentbook.excellentbook.service;

import com.excellentbook.excellentbook.dto.auth.RegisterUserDtoRequest;
import com.excellentbook.excellentbook.dto.auth.RegisterUserDtoResponse;
import com.excellentbook.excellentbook.dto.user.UserDtoRequest;
import com.excellentbook.excellentbook.dto.user.UserDtoResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    RegisterUserDtoResponse createUser(RegisterUserDtoRequest signUpDto);

    UserDtoResponse getUserById(Long id);

    UserDtoResponse updateUserById(Long id, UserDtoRequest userDtoRequest);
    UserDtoResponse updateUserPhoto(Long id, MultipartFile multipartFile);

    void deleteUserById(Long id);

}
