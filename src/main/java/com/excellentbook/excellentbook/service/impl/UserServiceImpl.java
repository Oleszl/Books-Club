package com.excellentbook.excellentbook.service.impl;

import com.excellentbook.excellentbook.dto.auth.SignUpDto;
import com.excellentbook.excellentbook.entity.Role;
import com.excellentbook.excellentbook.entity.User;
import com.excellentbook.excellentbook.exception.EmailExistException;
import com.excellentbook.excellentbook.exception.UsernameExistException;
import com.excellentbook.excellentbook.repository.RoleRepository;
import com.excellentbook.excellentbook.repository.UserRepository;
import com.excellentbook.excellentbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void createUser(SignUpDto signUpDto) {

        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new UsernameExistException(signUpDto.getEmail());
        }

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

        userRepository.save(user);


    }
}
