package com.zufar.service;


import com.zufar.dto.CategoryDTO;
import com.zufar.dto.OrderDTO;
import com.zufar.dto.OrderInput;
import com.zufar.entity.Category;
import com.zufar.entity.Order;
import com.zufar.repository.OrderRepository;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;


@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CategoryService categoryService;

    private static final Long orderId = 1L;
    private static final Long clientId = 1L;
    private static final Long categoryId = 1L;
    private static final Long invalidOrderId = 343L;
    
    private static final List<Order> orders = new ArrayList<>();
    private static final List<OrderDTO> orderDTOs = new ArrayList<>();

    private static final Category category = new Category(categoryId, "categoryName2");
    private static final CategoryDTO categoryDTO = new CategoryDTO(categoryId, "categoryName2");
    
    private static final List<Long> orderIds = new ArrayList<>();
    
    private static final Order orderEntity = new Order(orderId, "goodsName1", category, clientId);
    private static final OrderDTO orderDTO = new OrderDTO(orderId, "goodsName1", categoryDTO, clientId);
    private static final OrderInput orderUpdateInput = new OrderInput(orderId, "goodsName1", categoryId, clientId);


    @BeforeClass
    public static void setUp() {
        orderIds.add(orderId);
        orders.add(orderEntity);
        orderDTOs.add(orderDTO);
    }

    @Test
    public void whenGetAllCalledThenCollectionShouldBeReturned() {
        when(orderRepository.findAll()).thenReturn(orders);

        List<OrderDTO> expected = orderDTOs;
        List<OrderDTO> actual = this.orderService.getAll();

        verify(orderRepository, times(1)).findAll();
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void whenGetAllByIdsCalledThenCollectionShouldBeReturned() {
        when(orderRepository.findAllById(orderIds)).thenReturn(orders);

        List<OrderDTO> expected = orderDTOs;
        List<OrderDTO> actual = this.orderService.getAllByIds(orderIds);

        verify(orderRepository, times(1)).findAllById(orderIds);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void whenGetByIdCalledThenOrderShouldBeReturned() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
        
        OrderDTO expected = orderDTO;
        OrderDTO actual = this.orderService.getById(orderId);

        verify(orderRepository, times(1)).findById(orderId);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void whenSaveCalledThenOrderShouldBeReturned() {
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        when(categoryService.getById(categoryId)).thenReturn(categoryDTO);

        OrderDTO expected = orderDTO;
        OrderDTO actual = this.orderService.save(orderUpdateInput);

        verify(orderRepository, times(1)).save(orderEntity);
        verify(categoryService, times(1)).getById(categoryId);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void whenUpdateCalledThenOrderShouldBeReturned() {
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        when(categoryService.getById(categoryId)).thenReturn(categoryDTO);
        
        OrderDTO expected = orderDTO;
        OrderDTO actual = this.orderService.update(orderUpdateInput);

        verify(orderRepository, times(1)).save(orderEntity);
        verify(categoryService, times(1)).getById(categoryId);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void whenDeleteByIdCalled() {
        doNothing().when(orderRepository).deleteById(orderId);

        orderService.deleteById(orderId);

        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    public void whenDeleteAllByClientCalled() {
        doNothing().when(orderRepository).deleteAllByClientId(clientId);

        orderService.deleteAllByClientId(clientId);

        verify(orderRepository, times(1)).deleteAllByClientId(clientId);
    }
}
