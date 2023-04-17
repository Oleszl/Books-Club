package com.excellentbook.excellentbook.service.impl;

import com.excellentbook.excellentbook.dto.category.CategoryDto;
import com.excellentbook.excellentbook.entity.Category;
import com.excellentbook.excellentbook.repository.CategoryRepository;
import com.excellentbook.excellentbook.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
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

        log.info("List of categories was formed, all available categories: {}", categories);
        return categories.stream()
                .map(category -> mapper.map(category, CategoryDto.class))
                .toList();
    }

}
