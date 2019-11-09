package com.zufar.order_service.controller;

import com.zufar.order_service.api.OrderServiceEndPoint;
import com.zufar.dto.OrderDTO;
import com.zufar.order_service.service.OrderService;

import java.util.List;

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


@Validated
@RestController
@RequestMapping(value = "orders")
public class OrderController implements OrderServiceEndPoint {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @Override
    public @ResponseBody List<OrderDTO> getAll() {
        return this.orderService.getAll();
    }
    
    @PostMapping(value = "clients")
    @Override
    public @ResponseBody List<OrderDTO> getAllByClientIds(@RequestBody Long... clientIds) {
        return this.orderService.getAllByClientIds(clientIds);
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody ResponseEntity<OrderDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(this.orderService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public @ResponseBody ResponseEntity deleteById(@PathVariable Long id) {
        this.orderService.deleteById(id);
        return ResponseEntity.ok(String.format("The order with the given order id=[%d] was deleted.", id));
    }

    @DeleteMapping(value = "client/{clientId}")
    @Override
    public @ResponseBody ResponseEntity deleteAllByClientId(@PathVariable Long clientId) {
        this.orderService.deleteAllByClientId(clientId);
        return ResponseEntity.ok(String.format("The orders of the client with given client id=[%d] were deleted.", clientId));
    }

    @PostMapping
    @Override
    public @ResponseBody ResponseEntity save(@RequestBody OrderDTO order) {
        OrderDTO orderEntity = this.orderService.save(order);
        return ResponseEntity.ok(String.format("The order [%s] was saved.", orderEntity));
    }

    @PutMapping
    @Override
    public @ResponseBody ResponseEntity update(@RequestBody OrderDTO order) {
        OrderDTO orderEntity = this.orderService.update(order);
        return ResponseEntity.ok(String.format("The order [%s] was updated.", orderEntity));
    }
}
