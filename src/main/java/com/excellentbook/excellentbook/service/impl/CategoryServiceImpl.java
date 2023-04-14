package com.excellentbook.excellentbook.service.impl;

import com.excellentbook.excellentbook.dto.category.CategoryDto;
import com.excellentbook.excellentbook.entity.Category;
import com.excellentbook.excellentbook.repository.CategoryRepository;
import com.excellentbook.excellentbook.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> mapper.map(category, CategoryDto.class))
                .toList();
    }

}
