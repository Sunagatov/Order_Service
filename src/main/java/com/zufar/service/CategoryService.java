package com.zufar.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.zufar.dto.CategoryDTO;
import com.zufar.entity.Category;
import com.zufar.repository.CategoryRepository;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class CategoryService {

    private static final Logger LOGGER = LogManager.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getAll() {
        LOGGER.info("Get all categories.");
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
                .map(CategoryService::convertCategoryDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO getById(Long id) {
        LOGGER.info(String.format("Get category with id=[%d]", id));
        return categoryRepository.findById(id).map(CategoryService::convertCategoryDTO).orElse(null);
    }

    public static CategoryDTO convertCategoryDTO(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName()
        );
    }

    public static Category convertCategory(CategoryDTO category) {
        return new Category(
                category.getId(),
                category.getName()
        );
    }
}
