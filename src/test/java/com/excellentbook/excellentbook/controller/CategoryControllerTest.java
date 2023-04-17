package com.excellentbook.excellentbook.controller;

import com.excellentbook.excellentbook.service.CategoryService;
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
public class CategoryControllerTest {
    private static final String categoryLink = "/api/v1/category";
    private MockMvc mockMvc;

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;
    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .build();
    }
    @Test
    void getAllCategoryTest() throws Exception {
        mockMvc.perform(get(categoryLink)).andExpect(status().isOk());
        verify(categoryService).getAllCategory();
    }
}
