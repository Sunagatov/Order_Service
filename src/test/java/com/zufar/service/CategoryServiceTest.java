package com.zufar.service;


import com.zufar.entity.Category;
import com.zufar.exception.CategoryNotFoundException;
import com.zufar.exception.OrderNotFoundException;
import com.zufar.repository.CategoryRepository;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private static final Long categoryId = 1L;
    private static final Long invalidCategoryId = 3435L;
    private static final List<Category> categories = new ArrayList<>();
    private static final Category category = new Category(categoryId, "categoryName2");


    @BeforeClass
    public static void setUp() {
        categories.add(category);
    }

    @Test
    public void whenGetAllCalledThenCollectionShouldBeReturned() {
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> expected = categories;
        List<Category> actual = this.categoryService.getAll();

        verify(categoryRepository, times(1)).findAll();
        assertNotNull(actual);
        assertEquals(expected, actual);
    }


    @Test
    public void whenGetByIdCalledThenOrderShouldBeReturned() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryRepository.existsById(categoryId)).thenReturn(true);


        Category expected = category;
        Category actual = this.categoryService.getById(categoryId);

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).existsById(categoryId);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test(expected = CategoryNotFoundException.class)
    public void whenGetByIdWithInvalidIdThenOrderNotFoundExceptionShouldThrow() {
        when(categoryRepository.existsById(invalidCategoryId)).thenReturn(false);

        categoryService.getById(invalidCategoryId);
    }
}
