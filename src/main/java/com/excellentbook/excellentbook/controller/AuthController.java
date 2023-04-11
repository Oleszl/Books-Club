package com.excellentbook.excellentbook.controller;

import com.excellentbook.excellentbook.dto.auth.SignUpDtoRequest;
import com.excellentbook.excellentbook.dto.auth.SignUpDtoResponse;
import com.excellentbook.excellentbook.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignUpDtoResponse registerUser(@RequestBody SignUpDtoRequest signUpDto) {

        return userService.createUser(signUpDto);
    }


}
