package com.excellentbook.excellentbook.service;

import com.excellentbook.excellentbook.dto.auth.LoginDto;
import com.excellentbook.excellentbook.dto.auth.RegisterUserDtoRequest;
import com.excellentbook.excellentbook.dto.auth.RegisterUserDtoResponse;
import org.springframework.security.core.Authentication;

public interface AuthService {
    Authentication userAuthentication(LoginDto loginDto);

    RegisterUserDtoResponse registerUser(RegisterUserDtoRequest signUpDto);
}
