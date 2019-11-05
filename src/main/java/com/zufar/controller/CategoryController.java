package com.zufar.controller;

import com.zufar.entity.Category;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Api(value = "Category api")
@Validated
@RestController
@RequestMapping(value = "categories")
public class CategoryController {

    @ApiOperation(value = "View a list of order categories.", response = Category[].class, responseContainer = "List")
    @GetMapping
    public @ResponseBody ResponseEntity<Category[]> getCategories() {
        return new ResponseEntity<>(Category.values(), HttpStatus.OK);
    }
}
