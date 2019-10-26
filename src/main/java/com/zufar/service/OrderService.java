package com.zufar.service;

import com.zufar.entity.Order;
import com.zufar.dto.OrderDTO;
import com.zufar.entity.Category;
import com.zufar.repository.OrderRepository;
import com.zufar.exception.OrderNotFoundException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {

    private static final Logger LOGGER = LogManager.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final CategoryService categoryService;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        CategoryService categoryService) {
        this.orderRepository = orderRepository;
        this.categoryService = categoryService;
    }

    public List<Order> getAll() {
        List<Order> orders;
        try {
            orders = (List<Order>) this.orderRepository.findAll();
        } catch (Exception exception) {
            String databaseErrorMessage = "It is impossible to get all orders. There are some problems with a database.";
            LOGGER.error(databaseErrorMessage, exception);
            throw exception;
        }
        LOGGER.info("All orders were loaded from a database.");
        return orders;
    }

    public List<Order> getAllByIds(List<Long> ids) {
        List<Order> orders;
        try {
            orders = (List<Order>) this.orderRepository.findAllById(ids);
        } catch (Exception exception) {
            String databaseErrorMessage = String.format("It is impossible to get orders with ids=[%s]. There are some problems with a database.", ids);
            LOGGER.error(databaseErrorMessage, exception);
            throw exception;
        }
        LOGGER.info("All orders were loaded from a database.");
        return orders;
    }

    public List<Order> getAllByClientId(Long clientId) {
        List<Order> orders;
        try {
            orders = (List<Order>) this.orderRepository.findAllByClientId(clientId);
        } catch (Exception exception) {
            String errorMessage = String.format("It is impossible to get orders of the client with id=[%d]. There are some problems with a database.", clientId);
            LOGGER.error(errorMessage, exception);
            throw exception;
        }
        LOGGER.info(String.format("All orders of the client with id=[%d] were loaded from a database.", clientId));
        return orders;
    }

    public Order getById(Long id) {
        this.isExists(id);
        Order order;
        try {
            order = this.orderRepository.findById(id).orElse(null);
        } catch (Exception exception) {
            String errorMessage = String.format("It is impossible to get the order with id = [%d]. There are some problems with a database.", id);
            LOGGER.error(errorMessage, exception);
            throw exception;
        }
        LOGGER.info(String.format("The order with id=[%d] was loaded from a database.", id));
        return order;
    }

    public Order save(OrderDTO order) {
        Order orderEntity = this.convertToOrder(order);
        Order result;
        try {
            result = this.orderRepository.save(orderEntity);
        } catch (Exception exception) {
            String errorMessage = String.format("It is impossible to save the order - [%s]. There are some problems with a database.", order);
            LOGGER.error(errorMessage, exception);
            throw exception;
        }
        LOGGER.info(String.format("The order [%s] was saved in a database.", result));
        return result;
    }

    public Order update(OrderDTO order) {
        this.isExists(order.getId());
        Order orderEntity = this.convertToOrder(order);
        Order result;
        try {
            result = this.orderRepository.save(orderEntity);
        } catch (Exception exception) {
            String errorMessage = String.format("It is impossible to updated the order - [%s]. There are some problems with a database.", order);
            LOGGER.error(errorMessage, exception);
            throw exception;
        }
        LOGGER.info(String.format("The order [%s] was updated in a database.", result));
        return result;
    }

    public void deleteById(Long id) {
        this.isExists(id);
        try {
            this.orderRepository.deleteById(id);
        } catch (Exception exception) {
            String errorMessage = String.format("It is impossible to delete the order with id=[%d]. There are some problems with a database.", id);
            LOGGER.error(errorMessage, exception);
            throw exception;
        }
        LOGGER.info(String.format("The order with id=[%d] was deleted from a database.", id));
    }

    public void deleteAllByClientId(Long clientId) {
        try {
            this.orderRepository.deleteAllByClientId(clientId);
        } catch (Exception exception) {
            String errorMessage = String.format("It is impossible to delete orders with client id=[%d]. There are some problems with a database.", clientId);
            LOGGER.error(errorMessage, exception);
            throw exception;
        }
        LOGGER.info(String.format("The orders with client id=[%d] was deleted from a database.", clientId));
    }

    private void isExists(Long id) {
        if (!this.orderRepository.existsById(id)) {
            String errorMessage = String.format("The order with id = [%d] not found.", id);
            LOGGER.error(errorMessage);
            throw new OrderNotFoundException(errorMessage);
        }
    }

    private Order convertToOrder(OrderDTO orderDTO) {
        Category category = this.categoryService.getById(orderDTO.getCategoryId());
        return new Order(
                orderDTO.getId(),
                orderDTO.getGoodsName(),
                category,
                orderDTO.getClientId()
        );
    }
}
