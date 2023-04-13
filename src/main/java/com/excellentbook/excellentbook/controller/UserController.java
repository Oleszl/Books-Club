package com.excellentbook.excellentbook.controller;

import com.excellentbook.excellentbook.dto.user.UserDtoRequest;
import com.excellentbook.excellentbook.dto.user.UserDtoResponse;
import com.excellentbook.excellentbook.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDtoResponse getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserDtoResponse updateUser(@PathVariable Long id, @Valid @RequestBody UserDtoRequest userDtoRequest) {
        return userService.updateUserById(id, userDtoRequest);
    }

    @PatchMapping(path = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDtoResponse updateUserPhoto(@PathVariable("id") Long userId, @ModelAttribute("image") MultipartFile image) {
        return userService.updateUserPhoto(userId, image);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
