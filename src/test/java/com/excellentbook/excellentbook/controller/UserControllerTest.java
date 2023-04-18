package com.excellentbook.excellentbook.controller;

import com.excellentbook.excellentbook.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    private static final String userLink = "/api/v1/users";
    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
    }

    @Test
    void getUserTest() throws Exception {
        mockMvc.perform(get(userLink)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9Uljp05tyoFouPjiTeJ")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService).getUser();
    }

    @Test
    void getPersonalUserBooksByStatusTest() throws Exception {
        Long userId = 1L;
        mockMvc.perform(get(userLink + "/" + userId + "/personal-books")
                        .queryParam("status", "available")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService).getPersonalUserBooksByStatus(userId, "available");
    }

    @Test
    void getUserBooksByStatusTest() throws Exception {
        Long userId = 1L;
        mockMvc.perform(get(userLink + "/" + userId + "/books")
                        .queryParam("status", "available")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService).getUserBooksByStatus(userId, "available");
    }

    @Test
    void deleteUserByIdTest() throws Exception {
        mockMvc.perform(delete(userLink + "/1"))
                .andExpect(status().isNoContent());
        verify(userService).deleteUserById(1L);
    }

}
