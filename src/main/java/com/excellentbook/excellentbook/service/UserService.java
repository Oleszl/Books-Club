package com.excellentbook.excellentbook.service;

import com.excellentbook.excellentbook.dto.address.AddressDto;
import com.excellentbook.excellentbook.dto.user.UserBookDetailsDto;
import com.excellentbook.excellentbook.dto.user.UserDetailsDto;
import com.excellentbook.excellentbook.dto.user.UserDtoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {


    UserDtoResponse getUser();

    UserDtoResponse updateUserById(Long id, UserDetailsDto userDtoRequest);

    UserDtoResponse updateUserPhoto(Long id, MultipartFile multipartFile);

    UserDtoResponse updateUserAddress(Long id, AddressDto addressDto);

    void deleteUserById(Long id);

    List<UserBookDetailsDto> getUserBooksByStatus(Long id, String status);

    List<UserBookDetailsDto> getPersonalUserBooksByStatus(Long id, String status);


}
