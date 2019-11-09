package com.zufar.api;

import com.zufar.dto.Category;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(value = "Category api")
@RequestMapping("/default")
public interface CategoryServiceEndPoint {

    @ApiOperation(value = "View a list of order categories.", response = Category[].class, responseContainer = "List")
    ResponseEntity<Category[]> getCategories();
}
