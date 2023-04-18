package com.excellentbook.excellentbook.service;

import com.excellentbook.excellentbook.ModelUtils;
import com.excellentbook.excellentbook.dto.user.UserDetailsDto;
import com.excellentbook.excellentbook.dto.user.UserDtoResponse;
import com.excellentbook.excellentbook.entity.User;
import com.excellentbook.excellentbook.exception.ResourceNotFoundException;
import com.excellentbook.excellentbook.exception.UserNotFoundException;
import com.excellentbook.excellentbook.repository.UserRepository;
import com.excellentbook.excellentbook.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper mapper;

    @Test
    void getUserTest() {
        User user = ModelUtils.getUser();

        UserDtoResponse expectedResponse = new UserDtoResponse();
        expectedResponse.setId(user.getId());
        expectedResponse.setEmail(user.getEmail());

        Principal principal = new UsernamePasswordAuthenticationToken(user.getEmail(), null);
        SecurityContextHolder.getContext().setAuthentication((Authentication) principal);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(mapper.map(user, UserDtoResponse.class)).thenReturn(expectedResponse);

        UserDtoResponse actualResponse = userService.getUser();

        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getEmail(), actualResponse.getEmail());
    }
    @Test
    void getUserThrowUserNotFoundExceptionTest() {
        Principal principal = new UsernamePasswordAuthenticationToken("test@example.com", null);
        SecurityContextHolder.getContext().setAuthentication((Authentication) principal);

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUser());
    }

    @Test
    void updateUserByIdTest() {
        User user = ModelUtils.getUser();

        UserDetailsDto request = new UserDetailsDto();
        request.setEmail("test@gmail.com");
        request.setPassword("newpassword");

        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setEmail(request.getEmail());
        updatedUser.setPassword(request.getPassword());

        UserDtoResponse expectedResponse = new UserDtoResponse();
        expectedResponse.setId(1L);
        expectedResponse.setEmail(updatedUser.getEmail());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn(request.getPassword());
        when(mapper.map(updatedUser, UserDtoResponse.class)).thenReturn(expectedResponse);
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        UserDtoResponse actualResponse = userService.updateUserById(1L, request);

        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getEmail(), actualResponse.getEmail());
        verify(userRepository).save(updatedUser);
    }

    @Test
    void updateUserByIdThrowResourceNotFoundExceptionTest() {
        Long id = 1L;
        UserDetailsDto request = new UserDetailsDto();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updateUserById(id, request));
    }


}
