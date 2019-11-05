package com.zufar.controller;

import com.zufar.dto.OrderDTO;
import com.zufar.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import java.util.List;

@Api(value = "Order api")
@Validated
@RestController
@RequestMapping(value = "orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation(value = "View the order list.", response = OrderDTO.class, responseContainer = "List")
    @GetMapping
    public @ResponseBody ResponseEntity<List<OrderDTO>> getOrders() {
        return new ResponseEntity<>(this.orderService.getAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "View the order list with given order ids.", response = OrderDTO.class, responseContainer = "List")
    @PostMapping(value = "client")
    public @ResponseBody List<OrderDTO> getOrdersByClientId(@ApiParam(value = "An order client id which is used to get orders.", required = true) @RequestBody Long clientId) {
        return this.orderService.getAllByClientId(clientId);
    }

    @ApiOperation(value = "View the order with given order id.", response = OrderDTO.class)
    @GetMapping(value = "/{id}")
    public @ResponseBody ResponseEntity<OrderDTO> getOrder(@ApiParam(value = "An order id which is used to retrieve an order.", required = true) @PathVariable Long id) {
        return new ResponseEntity<>(this.orderService.getById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete the order with given order id.", response = ResponseEntity.class)
    @DeleteMapping(value = "/{id}")
    public @ResponseBody ResponseEntity deleteOrder(@ApiParam(value = "An order id which is used to delete an order.", required = true) @PathVariable Long id) {
        this.orderService.deleteById(id);
        return ResponseEntity.ok(String.format("The order with the given order id=[%d] was deleted.", id));
    }

    @ApiOperation(value = "Delete the orders of the client with given client id.", response = ResponseEntity.class)
    @DeleteMapping(value = "client/{clientId}")
    public @ResponseBody ResponseEntity deleteAllByClientId(@ApiParam(value = "A client id which is used to delete orders of specific client.", required = true) @PathVariable Long clientId) {
        this.orderService.deleteAllByClientId(clientId);
        return ResponseEntity.ok(String.format("The orders of the client with given client id=[%d] were deleted.", clientId));
    }

    @ApiOperation(value = "Save a new order.", response = ResponseEntity.class)
    @PostMapping
    public @ResponseBody ResponseEntity saveOrder(@ApiParam(value = "An order object which which will be saved.", required = true) @Valid @RequestBody OrderDTO order) {
        OrderDTO orderEntity = this.orderService.save(order);
        return ResponseEntity.ok(String.format("The order [%s] was saved.", orderEntity));
    }


    @ApiOperation(value = "Update an existed order.", response = ResponseEntity.class)
    @PutMapping
    public @ResponseBody ResponseEntity updateOrder(@ApiParam(value = "An order object which will be used to update an existed order.", required = true) @Valid @RequestBody OrderDTO order) {
        OrderDTO orderEntity = this.orderService.update(order);
        return ResponseEntity.ok(String.format("The order [%s] was updated.", orderEntity));
    }
}
