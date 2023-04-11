package com.excellentbook.excellentbook.service;

import com.excellentbook.excellentbook.dto.auth.SignUpDto;

public interface UserService {
    void createUser(SignUpDto signUpDto);

}
