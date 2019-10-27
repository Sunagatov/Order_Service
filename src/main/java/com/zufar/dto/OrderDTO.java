package com.zufar.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "Order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    @ApiModelProperty(notes = "Order id", name="id", required=true)
    private Long id;
    
    @ApiModelProperty(notes = "Goods name in an order", name="goodsName", required=true)
    private String goodsName;

    @ApiModelProperty(notes = "Goods category in an order", name="category_id", required=true)
    private CategoryDTO category;

    @ApiModelProperty(notes = "Client id in an order", name="clientId", required=true)
    private Long clientId;
}
