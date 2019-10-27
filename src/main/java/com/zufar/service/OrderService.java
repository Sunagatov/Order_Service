package com.zufar.service;

import com.zufar.dto.OrderDTO;
import com.zufar.entity.Category;
import com.zufar.entity.Order;
import com.zufar.dto.OrderInput;
import com.zufar.dto.CategoryDTO;
import com.zufar.repository.OrderRepository;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
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

    public List<OrderDTO> getAll() {
        LOGGER.info("Get all orders.");
        return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                .map(this::convertToOrderDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getAllByIds(List<Long> ids) {
        LOGGER.info(String.format("Get all orders with ids=[%s]. There are some problems with a database.", ids));
        return StreamSupport.stream(orderRepository.findAllById(ids).spliterator(), false)
                .map(this::convertToOrderDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO getById(Long id) {
        LOGGER.info(String.format("Get order with id=[%d]", id));
        return orderRepository.findById(id).map(this::convertToOrderDTO).orElse(null);
    }

    public OrderDTO save(OrderInput order) {
        Order orderEntity = convertToOrder(order);
        orderEntity = this.orderRepository.save(orderEntity);
        LOGGER.info(String.format("Order=[%s] was saved in a database successfully.", orderEntity));
        return convertToOrderDTO(orderEntity);
    }

    public OrderDTO update(OrderInput order) {
        Order orderEntity = convertToOrder(order);
        orderEntity = this.orderRepository.save(orderEntity);
        LOGGER.info(String.format("Order=[%s] was updated in a database successfully.", orderEntity));
        return convertToOrderDTO(orderEntity);
    }

    public void deleteById(Long id) {
        this.orderRepository.deleteById(id);
        LOGGER.info(String.format("Order with id=[%d] was deleted successfully.", id));
    }

    public void deleteAllByClientId(Long clientId) {
        this.orderRepository.deleteAllByClientId(clientId);
        LOGGER.info(String.format("The orders with client id=[%d] was deleted from a database.", clientId));
    }

    private Order convertToOrder(OrderInput orderDTO) {
        CategoryDTO category = this.categoryService.getById(orderDTO.getCategoryId());
        return new Order(
                orderDTO.getId(),
                orderDTO.getGoodsName(),
                CategoryService.convertCategory(category),
                orderDTO.getClientId()
        );
    }

    private OrderDTO convertToOrderDTO(Order order) {
        Category category = order.getCategory();
        return new OrderDTO(
                order.getId(),
                order.getGoodsName(),
                CategoryService.convertCategoryDTO(category),
                order.getClientId()
        );
    }
}
