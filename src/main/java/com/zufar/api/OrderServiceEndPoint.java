package com.zufar.api;

import com.zufar.dto.OrderDTO;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;


@Api(value = "Order api")
@RequestMapping("/default")
public interface OrderServiceEndPoint {

    @ApiOperation(value = "View the order list.", response = OrderDTO.class, responseContainer = "List")
    List<OrderDTO> getAll();

    @ApiOperation(value = "View the order list with given order ids.", response = OrderDTO.class, responseContainer = "List")
    List<OrderDTO> getAllByClientIds(@ApiParam(value = "An order client ids which is used to get orders.", required = true) Long... clientIds);

    @ApiOperation(value = "View the order with given order id.", response = OrderDTO.class)
    ResponseEntity<OrderDTO> getById(@ApiParam(value = "An order id which is used to retrieve an order.", required = true) Long id);

    @ApiOperation(value = "Delete the order with given order id.", response = ResponseEntity.class)
    ResponseEntity deleteById(@ApiParam(value = "An order id which is used to delete an order.", required = true) Long id);

    @ApiOperation(value = "Delete the orders of the client with given client id.", response = ResponseEntity.class)
    ResponseEntity deleteAllByClientId(@ApiParam(value = "A client id which is used to delete orders of specific client.", required = true) Long clientId);

    @ApiOperation(value = "Save a new order.", response = ResponseEntity.class)
    ResponseEntity save(@ApiParam(value = "An order object which which will be saved.", required = true) OrderDTO order);

    @ApiOperation(value = "Update an existed order.", response = ResponseEntity.class)
    ResponseEntity update(@ApiParam(value = "An order object which will be used to update an existed order.", required = true) OrderDTO order);
}
