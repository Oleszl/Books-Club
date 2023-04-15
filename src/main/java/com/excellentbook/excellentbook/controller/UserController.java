package com.excellentbook.excellentbook.controller;

import com.excellentbook.excellentbook.dto.address.AddressDto;
import com.excellentbook.excellentbook.dto.user.UserBookDetailsDto;
import com.excellentbook.excellentbook.dto.user.UserDetailsDto;
import com.excellentbook.excellentbook.dto.user.UserDtoResponse;
import com.excellentbook.excellentbook.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserDtoResponse getUser() {
        return userService.getUser();
    }

    @GetMapping("/{userId}/personal-books")
    public List<UserBookDetailsDto> getPersonalUserBooks(@PathVariable Long userId,
                                                         @RequestParam String status) {
        return userService.getPersonalUserBooksByStatus(userId, status);
    }

    @GetMapping("/{userId}/books")
    public List<UserBookDetailsDto> getUserBooks(@PathVariable Long userId,
                                                 @RequestParam String status) {
        return userService.getUserBooksByStatus(userId, status);
    }

    @PutMapping("/{userId}")
    public UserDtoResponse updateUser(@PathVariable("userId") Long id, @Valid @RequestBody UserDetailsDto userDetailsDto) {
        return userService.updateUserById(id, userDetailsDto);
    }

    @PutMapping("/{userId}/address")
    public UserDtoResponse updateUserAddress(@PathVariable("userId") Long id, @Valid @RequestBody AddressDto addressDto) {
        return userService.updateUserAddress(id, addressDto);
    }

    @PatchMapping(path = "/{userId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDtoResponse updateUserPhoto(@PathVariable("userId") Long userId, @ModelAttribute("image") MultipartFile image) {
        return userService.updateUserPhoto(userId, image);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("userId") Long id) {
        userService.deleteUserById(id);
    }

}
