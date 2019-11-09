package com.zufar.order_service_impl.controller;

import com.zufar.order_management_system_common.dto.OperationResult;
import com.zufar.order_management_system_common.dto.OrderDTO;
import com.zufar.order_service_api.endpoint.OrderServiceEndPoint;
import com.zufar.order_service_impl.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Api(value = "Order api")
@Validated
@RestController
@RequestMapping(value = "orders")
public class OrderController implements OrderServiceEndPoint<OrderDTO, Long> {

    private final OrderService orderService;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @Override
    @ApiOperation(value = "View the order list.", response = OrderDTO.class, responseContainer = "List")
    public @ResponseBody
    List<OrderDTO> getAll() {
        return this.orderService.getAll();
    }

    @PostMapping(value = "clients")
    @Override
    @ApiOperation(value = "View the order list with given order ids.", response = OrderDTO.class, responseContainer = "List") public @ResponseBody
    List<OrderDTO> getAllByClientIds(@ApiParam(value = "An order client ids which is used to get orders.", required = true) @RequestBody Long... clientIds) {
        return this.orderService.getAllByClientIds(clientIds);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "View the order with given order id.", response = OrderDTO.class)
    public @ResponseBody ResponseEntity<OrderDTO> getById(@ApiParam(value = "An order id which is used to retrieve an order.", required = true) @PathVariable Long id) {
        return new ResponseEntity<>(this.orderService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @Override
    @ApiOperation(value = "Delete the order with given order id.", response = ResponseEntity.class)
    public @ResponseBody ResponseEntity deleteById(@ApiParam(value = "An order id which is used to delete an order.", required = true) @PathVariable Long id) {
        this.orderService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(OperationResult.builder()
                        .timestamp(LocalDateTime.now().format(formatter))
                        .message(String.format("The order with the given order id=[%d] was deleted.", id))
                        .status(HttpStatus.OK.toString())
                        .build());
    }

    @DeleteMapping(value = "client/{clientId}")
    @Override
    @ApiOperation(value = "Delete the orders of the client with given client id.", response = ResponseEntity.class)
    public @ResponseBody ResponseEntity deleteAllByClientId(@ApiParam(value = "A client id which is used to delete orders of specific client.", required = true) @PathVariable(value = "clientId") Long clientId) {
        this.orderService.deleteAllByClientId(clientId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(OperationResult.builder()
                        .timestamp(LocalDateTime.now().format(formatter))
                        .message(String.format("The orders of the client with given client id=[%d] were deleted.", clientId))
                        .status(HttpStatus.OK.toString())
                        .build());
    }

    @PostMapping
    @Override
    @ApiOperation(value = "Save a new order.", response = ResponseEntity.class)
    public @ResponseBody ResponseEntity save(@ApiParam(value = "An order object which which will be saved.", required = true) @NotNull @Valid @RequestBody OrderDTO order) {
        OrderDTO savedOrder = this.orderService.save(order);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(OperationResult.builder()
                        .timestamp(LocalDateTime.now().format(formatter))
                        .message((String.format("The order [%s] was saved.", savedOrder)))
                        .status(HttpStatus.CREATED.toString())
                        .build());
    }

    @PutMapping
    @Override
    @ApiOperation(value = "Update an existed order.", response = ResponseEntity.class)
    public @ResponseBody ResponseEntity update(@ApiParam(value = "An order object which will be used to update an existed order.", required = true) @NotNull @Valid @RequestBody OrderDTO order) {
        OrderDTO updatedOrder = this.orderService.update(order);
        return ResponseEntity.status(HttpStatus.OK)
                .body(OperationResult.builder()
                        .timestamp(LocalDateTime.now().format(formatter))
                        .message((String.format("The order [%s] was updated.", updatedOrder)))
                        .status(HttpStatus.OK.toString())
                        .build());
    }
}
