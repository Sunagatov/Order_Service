package com.zufar.controller;

import com.zufar.entity.Category;
import com.zufar.entity.Order;
import com.zufar.service.CategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Api(value = "Category",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@RestController
@RequestMapping(value = "categories", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ApiOperation(value = "View a list of order categories.", response = Category.class, responseContainer = "List")
    @GetMapping
    public List<Category> getCategories() {
        return this.categoryService.getAll();
    }


    @ApiOperation(value = "View the category with given id.", response = Order.class)
    @GetMapping(value = "/{id}")
    public @ResponseBody Category getCategory(@ApiParam(value = "An id which is used to retrieve an order category.", required = true) @PathVariable Long id) {
        return this.categoryService.getById(id);
    }
}