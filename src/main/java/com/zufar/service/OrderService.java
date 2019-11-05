package com.zufar.service;

import com.zufar.entity.Order;
import com.zufar.dto.OrderDTO;
import com.zufar.exception.ClientNotFoundException;
import com.zufar.exception.InternalServerException;
import com.zufar.repository.OrderRepository;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrderService {

    private static final Logger LOGGER = LogManager.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final ClientService clientService;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        ClientService clientService) {
        this.orderRepository = orderRepository;
        this.clientService = clientService;
    }

    public List<OrderDTO> getAll() {
        LOGGER.info("Get all orders.");
        return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                .map(this::convertToOrderDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getAllByClientId(Long id) {
        LOGGER.info(String.format("Get all orders of client with id=[%d].", id));
        return StreamSupport.stream(orderRepository.findAllByClientId(id).spliterator(), false)
                .map(this::convertToOrderDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO getById(Long id) {
        LOGGER.info(String.format("Get order with id=[%d]", id));
        return orderRepository.findById(id).map(this::convertToOrderDTO).orElse(null);
    }

    @Transactional
    public OrderDTO save(OrderDTO order) {
        this.isClientExists(order.getClientId());
        Order orderEntity = convertToOrder(order);
        orderEntity = this.orderRepository.save(orderEntity);
        LOGGER.info(String.format("Order=[%s] was saved in a database successfully.", orderEntity));
        return convertToOrderDTO(orderEntity);
    }

    @Transactional
    public OrderDTO update(OrderDTO order) {
        this.isClientExists(order.getClientId());
        Order orderEntity = convertToOrder(order);
        orderEntity = this.orderRepository.save(orderEntity);
        LOGGER.info(String.format("Order=[%s] was updated in a database successfully.", orderEntity));
        return convertToOrderDTO(orderEntity);
    }

    private void isClientExists(Long clientId) {
        ResponseEntity<Boolean> result = this.clientService.isClientExists(clientId);
        if (result == null || result.getBody() == null || result.getStatusCode() != HttpStatus.OK) {
            String errorMessage = String.format("There is no info about client with id=[%d].", clientId);
            LOGGER.error(errorMessage);
            throw new InternalServerException(errorMessage);
        }
        if (!result.getBody()) {
            String errorMessage = String.format("There is no client with id=[%d].", clientId);
            LOGGER.error(errorMessage);
            throw new ClientNotFoundException(errorMessage);
        }
        LOGGER.info(String.format("There is client with id=[%d].", clientId));
    }

    public void deleteById(Long id) {
        this.orderRepository.deleteById(id);
        LOGGER.info(String.format("Order with id=[%d] was deleted successfully.", id));
    }

    @Transactional
    public void deleteAllByClientId(Long clientId) {
        if (!this.orderRepository.existsByClientId(clientId)) {
            LOGGER.info(String.format("There are no orders with client id=[%d].", clientId));
            return;
        }
        this.orderRepository.deleteAllByClientId(clientId);
        LOGGER.info(String.format("The orders with client id=[%d] was deleted from a database.", clientId));
    }

    private Order convertToOrder(OrderDTO orderDTO) {
        return new Order(
                orderDTO.getId(),
                orderDTO.getGoodsName(),
                orderDTO.getCategory(),
                orderDTO.getClientId()
        );
    }

    private OrderDTO convertToOrderDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getGoodsName(),
                order.getCategory(),
                order.getClientId()
        );
    }
}
