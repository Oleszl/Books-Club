package com.excellentbook.excellentbook.service;

import com.excellentbook.excellentbook.dto.auth.SignUpDtoRequest;
import com.excellentbook.excellentbook.dto.auth.SignUpDtoResponse;

public interface UserService {
    SignUpDtoResponse createUser(SignUpDtoRequest signUpDto);

}
