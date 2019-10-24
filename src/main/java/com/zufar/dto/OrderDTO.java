package com.zufar.dto;

import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    @ApiModelProperty(notes = "order id", name="id")
    private Long id;

    @ApiModelProperty(notes = "goods name of an order", name="goodsName", required=true)
    @NotEmpty(message = "Please provide an order's goods name. It is empty")
    @NotNull(message = "Please provide an order' goods name")
    @Size(min = 5, max = 160, message = "Name length should be from 5 to 160")
    private String goodsName;

    @ApiModelProperty(notes = "category id of an order", name="category_id", required=true)
    @NotNull(message = "Please provide an order's category id")
    private Long categoryId;

    @ApiModelProperty(notes = "client id of an order", name="clientId", required=true)
    @NotNull(message = "Please provide an order client's id")
    private Long clientId;
}
