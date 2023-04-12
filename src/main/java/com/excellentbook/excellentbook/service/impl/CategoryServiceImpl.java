package com.excellentbook.excellentbook.service.impl;

import com.excellentbook.excellentbook.dto.category.CategoryDto;
import com.excellentbook.excellentbook.repository.CategoryRepository;
import com.excellentbook.excellentbook.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public CategoryDto getAllCategory() {
        return null;
    }

    @Override
    public CategoryDto getCategoryById() {
        return null;
    }
}
