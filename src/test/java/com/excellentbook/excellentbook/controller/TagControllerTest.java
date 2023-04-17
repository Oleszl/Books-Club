package com.excellentbook.excellentbook.controller;

import com.excellentbook.excellentbook.service.CategoryService;
import com.excellentbook.excellentbook.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TagControllerTest {
    private static final String tagLink = "/api/v1/tags";
    private MockMvc mockMvc;

    @InjectMocks
    private TagController tagController;

    @Mock
    private TagService tagService;
    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(tagController)
                .build();
    }
    @Test
    void getAllTagsTest() throws Exception {
        mockMvc.perform(get(tagLink)).andExpect(status().isOk());
        verify(tagService).getAllTags();
    }
}
