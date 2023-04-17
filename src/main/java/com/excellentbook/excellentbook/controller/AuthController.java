package com.excellentbook.excellentbook.controller;

import com.excellentbook.excellentbook.dto.auth.JwtAuthResponse;
import com.excellentbook.excellentbook.dto.auth.LoginDto;
import com.excellentbook.excellentbook.dto.auth.RegisterUserDtoRequest;
import com.excellentbook.excellentbook.dto.auth.RegisterUserDtoResponse;
import com.excellentbook.excellentbook.security.jwt.JwtTokenProvider;
import com.excellentbook.excellentbook.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterUserDtoResponse registerUser(@Valid @RequestBody RegisterUserDtoRequest registerUserDtoRequest) {
        return authService.registerUser(registerUserDtoRequest);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtAuthResponse> authenticate(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication = authService.userAuthentication(loginDto);

        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthResponse(token));
    }


}
