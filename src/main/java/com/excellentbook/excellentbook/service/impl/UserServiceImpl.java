package com.excellentbook.excellentbook.service.impl;

import com.excellentbook.excellentbook.dto.auth.SignUpDtoRequest;
import com.excellentbook.excellentbook.dto.auth.SignUpDtoResponse;
import com.excellentbook.excellentbook.entity.Role;
import com.excellentbook.excellentbook.entity.User;
import com.excellentbook.excellentbook.exception.EmailExistException;
import com.excellentbook.excellentbook.repository.RoleRepository;
import com.excellentbook.excellentbook.repository.UserRepository;
import com.excellentbook.excellentbook.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final ModelMapper mapper;


    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SignUpDtoResponse createUser(SignUpDtoRequest signUpDto) {

        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new EmailExistException(signUpDto.getEmail());
        }

        User user = new User();
        user.setFirstName(signUpDto.getFirstName());
        user.setLastName(signUpDto.getLastName());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();

        user.setRoles(Collections.singleton(roles));

        return mapper.map(userRepository.save(user),SignUpDtoResponse.class);
    }
}
