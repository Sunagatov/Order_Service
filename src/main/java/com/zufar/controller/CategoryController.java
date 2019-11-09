package com.zufar.controller;

import com.zufar.dto.Category;
import com.zufar.api.CategoryServiceEndPoint;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "categories")
public class CategoryController implements CategoryServiceEndPoint {

    @Override
    @GetMapping
    public ResponseEntity<Category[]> getCategories() {
        return new ResponseEntity<>(Category.values(), HttpStatus.OK);
    }
}
