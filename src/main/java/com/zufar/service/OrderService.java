package com.zufar.service;

import com.zufar.entity.Category;
import com.zufar.exception.OrderNotFoundException;
import com.zufar.entity.Order;
import com.zufar.dto.OrderDTO;
import com.zufar.repository.CategoryRepository;
import com.zufar.repository.OrderRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class OrderService {

    private static final Logger LOGGER = LogManager.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        CategoryRepository categoryRepository) {
        this.orderRepository = orderRepository;
        this.categoryRepository = categoryRepository;
    }

    public Collection<Order> getAll() {
        Collection<Order> orders;
        try {
            orders = (Collection<Order>) this.orderRepository.findAll();
            LOGGER.info("All orders were loaded from a database.");
        } catch (Exception exception) {
            final String databaseErrorMessage = "It is impossible to get all clients. There are some problems with a database.";
            LOGGER.error(databaseErrorMessage, exception);
            throw new OrderNotFoundException(databaseErrorMessage, exception);
        }
        return orders;
    }

    public Collection<Order> getAllByClientId(Long clientId) {
        Collection<Order> orders;
        try {
            orders = (Collection<Order>) this.orderRepository.findAllByClientId(clientId);
            LOGGER.info(String.format("All orders of the client with id=[%d] were loaded from a database.", clientId));
        } catch (Exception exception) {
            final String databaseErrorMessage = String.format("It is impossible to get orders of the client with id=[%d]. There are some problems with a database.", clientId);
            LOGGER.error(databaseErrorMessage, exception);
            throw new OrderNotFoundException(databaseErrorMessage, exception);
        }
        return orders;
    }

    public Order getById(Long id) {
        this.isExists(id);
        Order order;
        try {
            order = this.orderRepository.findById(id).orElse(null);
            LOGGER.info(String.format("The order with id=[%d] were loaded from a database.", id));
        } catch (Exception exception) {
            final String databaseErrorMessage = String.format("It is impossible to get the client with id = [%d]. There are some problems with a database.", id);
            LOGGER.error(databaseErrorMessage, exception);
            throw new OrderNotFoundException(databaseErrorMessage, exception);
        }
        return order;
    }

    public Order save(OrderDTO order) {
        final String mainErrorMessage = String.format("It is impossible to save the order - [%b].", order);
        final Category category;
        try {
            category = this.categoryRepository.findById(order.getCategoryId()).orElse(null);
            LOGGER.info(String.format("Category [%s] was loaded from a database.", category));
        } catch (Exception exception) {
            final String databaseErrorMessage = mainErrorMessage +
                    String.format("It is impossible to get the order category with id = [%d]. There are some problems with a database.", order.getCategoryId());
            LOGGER.error(databaseErrorMessage, exception);
            throw new OrderNotFoundException(databaseErrorMessage, exception);
        }
        final Order orderEntity = this.convertToOrder(order, category);
        final Order result;
        try {
            result = this.orderRepository.save(orderEntity);
        } catch (Exception exception) {
            final String databaseErrorMessage = " There are some problems with a database.";
            LOGGER.error(mainErrorMessage + databaseErrorMessage, exception);
            throw exception;
        }
        LOGGER.info("All ordered were loaded from a database.");
        return result;
    }

    public Order update(OrderDTO order) {
        this.isExists(order.getId());
        return this.save(order);
    }

    public void deleteById(Long id) {
        this.isExists(id);
        this.orderRepository.deleteById(id);
        LOGGER.info(String.format("The order with id=[%d] was deleted from a database.", id));

    }

    public void deleteAllByClientId(Long clientId) {
        this.orderRepository.deleteAllByClientId(clientId);
        LOGGER.info(String.format("The orders with client id=[%d] was deleted from a database.", clientId));
    }

    private void isExists(Long id) {
        if (!this.orderRepository.existsById(id)) {
            final String errorMessage = String.format("The order with id = [%d] not found.", id);
            LOGGER.error(errorMessage);
            throw new OrderNotFoundException(errorMessage);
        }
    }

    private Order convertToOrder(OrderDTO orderDTO, Category category) {
        return new Order(
                orderDTO.getId(),
                orderDTO.getGoodsName(),
                category,
                orderDTO.getClientId()
        );
    }
}
