package com.zufar.order_service.service;

import com.zufar.order_service.entity.Category;
import com.zufar.order_service.entity.Order;
import com.zufar.order_service.dto.OrderDTO;
import com.zufar.order_service.exception.CategoryNotFoundException;
import com.zufar.order_service.exception.OrderNotFoundException;
import com.zufar.order_service.repository.CategoryRepository;
import com.zufar.order_service.repository.OrderRepository;

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

    public Collection<Order> getAll(Iterable<Long> ids) {
        Collection<Order> orders;
        try {
            if (ids == null) {
                orders = (Collection<Order>) this.orderRepository.findAll();
            } else {
                orders = (Collection<Order>) this.orderRepository.findAllById(ids);
            }
        } catch (Exception exception) {
            final String databaseErrorMessage = "It is impossible to get all clients. There are some problems with a database.";
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
        } catch (Exception exception) {
            final String databaseErrorMessage =
                    String.format("It is impossible to get the client with id = [%d]. There are some problems with a database.", id);
            LOGGER.error(databaseErrorMessage, exception);
            throw new OrderNotFoundException(databaseErrorMessage, exception);
        }
        return order;
    }

    public Order save(OrderDTO order) {
        final Category category = this.categoryRepository.findById(order.getCategoryId()).orElse(null);
        if (category == null) {
            final String databaseErrorMessage = 
                    String.format("It is impossible to save the order - [%b]. There is no the category with id=[%d].", order, order.getCategoryId());
            LOGGER.error(databaseErrorMessage);
            throw new CategoryNotFoundException(databaseErrorMessage);
        }
        final Order orderEntity = this.convertToOrder(order, category);
        return this.orderRepository.save(orderEntity);
    }

    public Order update(OrderDTO order) {
        this.isExists(order.getId());
        final Category category = this.categoryRepository.findById(order.getCategoryId()).orElse(null);
        if (category == null) {
            final String databaseErrorMessage =
                    String.format("It is impossible to update the order - [%b]. There is no the category with id=[%d].", order, order.getCategoryId());
            LOGGER.error(databaseErrorMessage);
            throw new CategoryNotFoundException(databaseErrorMessage);
        }
        final Order orderEntity = this.convertToOrder(order, category);
        return this.orderRepository.save(orderEntity);
    }

    public void deleteById(Long id) {
        this.isExists(id);
        this.orderRepository.deleteById(id);
    }

    public void deleteAll(Iterable<Long> ids) {
        final Iterable<Order> orders = this.orderRepository.findAllById(ids);
        this.orderRepository.deleteAll(orders);
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
