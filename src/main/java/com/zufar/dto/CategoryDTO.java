package com.zufar.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("Order category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    @ApiModelProperty(notes = "Category id", name = "id", required = true)
    private Long id;

    @ApiModelProperty(notes = "Category name", name = "name", required = true)
    private String name;
}
