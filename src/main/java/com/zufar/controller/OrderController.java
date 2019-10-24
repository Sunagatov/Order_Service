package com.zufar.controller;

import com.zufar.entity.Category;
import com.zufar.dto.OrderDTO;
import com.zufar.entity.Order;
import com.zufar.repository.CategoryRepository;
import com.zufar.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.util.Collection;

@Api(value = "orders")
@Validated
@RestController
@RequestMapping(value = "orders", produces = {MediaType.APPLICATION_JSON_VALUE})
public class OrderController {

    private final OrderService orderService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public OrderController(OrderService orderService,
                           CategoryRepository categoryRepository) {
        this.orderService = orderService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    @ApiOperation(value = "View the list of orders", response = Collection.class)
    public Collection<Order> getOrders() {
        return this.orderService.getAll();
    }

    @GetMapping(value = "clientId={clientId}")
    @ApiOperation(value = "View the list of orders", response = Collection.class)
    public Collection<Order> getOrdersBy(@Valid @ApiParam(value = "Id list which is used to retrieve orders") @PathVariable(required = false) Long clientId) {
        return this.orderService.getAllByClientId(clientId);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "View the order with given id", response = Order.class)
    public @ResponseBody  Order getOrder(@Valid @Min(0) @NotNull @ApiParam(value = "An id which is used to retrieve an order", required = true) @PathVariable Long id) {
        return this.orderService.getById(id);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete the order with given id", response = ResponseEntity.class)
    public @ResponseBody  ResponseEntity deleteOrder(@Valid @Min(0) @NotNull  @ApiParam(value = "An id which is used to delete order", required = true) @PathVariable Long id) {
        this.orderService.deleteById(id);
        return ResponseEntity.ok(String.format("The order with id=[%d] was deleted", id));
    }

    @DeleteMapping(value = "orders/clientId={id}")
    @ApiOperation(value = "Delete the orders with given client id", response = ResponseEntity.class)
    public @ResponseBody  ResponseEntity deleteOrders(@Valid @NotNull @ApiParam(value = "Client id which is used  to delete orders", required = true) @PathVariable Long id) {
        this.orderService.deleteAllByClientId(id);
        return ResponseEntity.ok(String.format("The orders with client id=[%d] were deleted", id));
    }

    @PostMapping
    @ApiOperation(value = "Save a new order", response = ResponseEntity.class)
    public @ResponseBody ResponseEntity saveOrder(@Valid @NotNull @ApiParam(value = "An order object which which will be saved", required = true) @RequestBody OrderDTO order) {
        final Order orderEntity = this.orderService.save(order);
        return ResponseEntity.ok(String.format("The order [%s] was saved", orderEntity));
    }

    @PutMapping
    @ApiOperation(value = "Update an existed order", response = ResponseEntity.class)
    public @ResponseBody ResponseEntity updateOrder(@Valid @NotNull @ApiParam(value = "An order object which which will be used to update an existed order", required = true) @RequestBody OrderDTO order) {
        final Order orderEntity = this.orderService.update(order);
        return ResponseEntity.ok(String.format("The order [%s] was updated", orderEntity));
    }

    @GetMapping(value = "categories")
    @ApiOperation(value = "View the list of categories", response = Iterable.class)
    public @ResponseBody Iterable<Category> getCategories() {
        return this.categoryRepository.findAll();
    }
}
