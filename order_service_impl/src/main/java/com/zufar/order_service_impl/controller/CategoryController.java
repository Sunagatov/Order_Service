package com.zufar.order_service_impl.controller;

import com.zufar.order_management_system_common.dto.Category;
import com.zufar.order_service_api.endpoint.CategoryServiceEndPoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Category api")
@RestController
@RequestMapping(value = "categories")
public class CategoryController implements CategoryServiceEndPoint<Category> {

    @Override
    @GetMapping
    @ApiOperation(value = "View a list of order categories.", response = Category[].class, responseContainer = "List")
    public ResponseEntity<Category[]> getCategories() {
        return new ResponseEntity<>(Category.values(), HttpStatus.OK);
    }
}
