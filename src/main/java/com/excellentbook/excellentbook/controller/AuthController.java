package com.excellentbook.excellentbook.controller;

import com.excellentbook.excellentbook.dto.auth.SignUpDto;
import com.excellentbook.excellentbook.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {

        userService.createUser(signUpDto);

        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);

    }

}
