package com.excellentbook.excellentbook.service.impl;

import com.excellentbook.excellentbook.dto.auth.LoginDto;
import com.excellentbook.excellentbook.dto.auth.RegisterUserDtoRequest;
import com.excellentbook.excellentbook.dto.auth.RegisterUserDtoResponse;
import com.excellentbook.excellentbook.entity.Address;
import com.excellentbook.excellentbook.entity.Role;
import com.excellentbook.excellentbook.entity.User;
import com.excellentbook.excellentbook.exception.CustomUsernameNotFoundException;
import com.excellentbook.excellentbook.exception.EmailExistException;
import com.excellentbook.excellentbook.exception.InvalidPasswordException;
import com.excellentbook.excellentbook.exception.RoleNotFoundException;
import com.excellentbook.excellentbook.repository.RoleRepository;
import com.excellentbook.excellentbook.repository.UserRepository;
import com.excellentbook.excellentbook.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ModelMapper mapper;

    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager,
                           PasswordEncoder passwordEncoder, RoleRepository roleRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    @Override
    public Authentication userAuthentication(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() ->
                        new CustomUsernameNotFoundException("Provided email address is invalid"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            log.error("Invalid password provided for user with email: {}", user.getEmail());
            throw new InvalidPasswordException("Provided password is invalid");
        }
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),
                        loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("User with email: {} was successfully added to context", user.getEmail());
        return authentication;
    }

    @Override
    public RegisterUserDtoResponse registerUser(RegisterUserDtoRequest signUpDto) {

        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new EmailExistException(signUpDto.getEmail());
        }
        User user = new User();
        user.setFirstName(signUpDto.getFirstName());
        user.setLastName(signUpDto.getLastName());
        user.setEmail(signUpDto.getEmail());
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setPhoneNumber(signUpDto.getPhoneNumber());

        Address address = mapper.map(signUpDto.getAddress(), Address.class);
        user.setAddress(address);

        Role roles = roleRepository.findRoleByName("USER")
                .orElseThrow(() -> new RoleNotFoundException("USER"));
        user.setRoles(Collections.singleton(roles));

        log.info("User with id: {} was saved", user.getId());
        return mapper.map(userRepository.save(user), RegisterUserDtoResponse.class);
    }
}
