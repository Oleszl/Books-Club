package com.excellentbook.excellentbook.service;

import com.excellentbook.excellentbook.ModelUtils;
import com.excellentbook.excellentbook.dto.category.CategoryDto;
import com.excellentbook.excellentbook.repository.CategoryRepository;
import com.excellentbook.excellentbook.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    ModelMapper mapper;

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Test
    void getAllCategoryTest() {

        when(categoryRepository.findAll()).thenReturn(ModelUtils.getCategories());

        CategoryDto categoryDto1 = new CategoryDto();
        categoryDto1.setId(1L);
        categoryDto1.setName("Category1");

        CategoryDto categoryDto2 = new CategoryDto();
        categoryDto2.setId(2L);
        categoryDto2.setName("Category2");

        when(mapper.map(ModelUtils.getCategories().get(0), CategoryDto.class)).thenReturn(categoryDto1);
        when(mapper.map(ModelUtils.getCategories().get(1), CategoryDto.class)).thenReturn(categoryDto2);

        List<CategoryDto> categoryDtos = categoryService.getAllCategory();

        assertEquals(2, categoryDtos.size());
        assertEquals("Category1", categoryDtos.get(0).getName());
        assertEquals("Category2", categoryDtos.get(1).getName());


    }
}
