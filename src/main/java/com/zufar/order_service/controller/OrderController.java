package com.zufar.order_service.controller;

import com.zufar.order_service.dto.OrderDTO;
import com.zufar.order_service.entity.Category;
import com.zufar.order_service.entity.Order;
import com.zufar.order_service.repository.CategoryRepository;
import com.zufar.order_service.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

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
    public Collection<Order> getOrders(@Valid @ApiParam(value = "Id list which is used to retrieve orders") @RequestBody(required = false) Iterable<Long> orderIds) {
        return this.orderService.getAll(orderIds);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "View the order with given id", response = Order.class)
    public Order getOrder(@Valid @Min(0) @NotNull @ApiParam(value = "An id which is used to retrieve an order", required = true) @PathVariable Long id) {
        return this.orderService.getById(id);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete the order with given id", response = ResponseEntity.class)
    public ResponseEntity deleteOrder(@Valid @Min(0) @NotNull  @ApiParam(value = "An id which is used to delete order", required = true) @PathVariable Long id) {
        this.orderService.deleteById(id);
        return ResponseEntity.ok(String.format("The order with id=[%d] was deleted", id));
    }

    @DeleteMapping
    @ApiOperation(value = "Delete the orders with given ids", response = ResponseEntity.class)
    public ResponseEntity deleteOrders(@Valid @NotNull @ApiParam(value = "Id list which is used  to delete orders", required = true) @RequestBody Iterable<Long> ids) {
        this.orderService.deleteAll(ids);
        return ResponseEntity.ok(String.format("The orders with ids=[%s] were deleted", ids));
    }

    @PostMapping
    @ApiOperation(value = "Save a new order", response = ResponseEntity.class)
    public ResponseEntity saveOrder(@Valid @NotNull @ApiParam(value = "An order object which which will be saved", required = true) @RequestBody OrderDTO order) {
        final Order orderEntity = this.orderService.save(order);
        return ResponseEntity.ok(String.format("The order [%s] was saved", orderEntity));
    }

    @PutMapping
    @ApiOperation(value = "Update an existed order", response = ResponseEntity.class)
    public ResponseEntity updateOrder(@Valid @NotNull @ApiParam(value = "An order object which which will be used to update an existed order", required = true) @RequestBody OrderDTO order) {
        final Order orderEntity = this.orderService.update(order);
        return ResponseEntity.ok(String.format("The order [%s] was updated", orderEntity));
    }

    @GetMapping(value = "categories")
    @ApiOperation(value = "View the list of categories", response = Iterable.class)
    public Iterable<Category> getCategories() {
        return this.categoryRepository.findAll();
    }
}
