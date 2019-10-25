package com.zufar.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel("Order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    @ApiModelProperty(notes = "Order id", name = "id")
    private Long id;

    @ApiModelProperty(notes = "Goods name in an order", name = "goodsName", required = true)
    @NotEmpty(message = "Please provide a goods name in an order. It is empty.")
    @NotNull(message = "Please provide a goods name in an order. It is absent.")
    @Size(min = 5, max = 160, message = "Goods name length in an order should be from 5 to 160.")
    private String goodsName;

    @ApiModelProperty(notes = "Goods category id in an order", name = "category_id", required = true)
    @NotNull(message = "Please provide a category id in a order. It is absent.")
    private Long categoryId;

    @ApiModelProperty(notes = "client id of an order", name = "clientId", required = true)
    @NotNull(message = "Please provide a client id in a order.")
    private Long clientId;
}
