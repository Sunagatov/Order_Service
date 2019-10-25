package com.zufar.service;

import java.util.List;

import com.zufar.entity.Category;
import com.zufar.repository.CategoryRepository;
import com.zufar.exception.CategoryNotFoundException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CategoryService {

    private static final Logger LOGGER = LogManager.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        List<Category> categories;
        try {
            categories = (List<Category>) this.categoryRepository.findAll();
            LOGGER.info("All categories were loaded from a database.");
        } catch (Exception exception) {
            final String databaseErrorMessage = "It is impossible to get all categories. There are some problems with a database.";
            LOGGER.error(databaseErrorMessage, exception);
            throw exception;
        } 
        return categories;
    }

    public Category getById(Long id) {
        this.isExists(id);
        Category category;
        try {
            category = this.categoryRepository.findById(id).orElse(null);
            LOGGER.info(String.format("The category with id=[%d] were loaded from a database.", id));
        } catch (Exception exception) {
            final String databaseErrorMessage = String.format("It is impossible to get the client with id = [%d]. There are some problems with a database.", id);
            LOGGER.error(databaseErrorMessage, exception);
            throw exception;
        }
        return category;
    }

    private void isExists(Long id) {
        if (!this.categoryRepository.existsById(id)) {
            final String errorMessage = String.format("The category with id = [%d] not found.", id);
            LOGGER.error(errorMessage);
            throw new CategoryNotFoundException(errorMessage);
        }
    }
}
