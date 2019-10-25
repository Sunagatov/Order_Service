package com.zufar.service;


import com.zufar.dto.OrderDTO;
import com.zufar.entity.Category;
import com.zufar.entity.Order;
import com.zufar.exception.CategoryNotFoundException;
import com.zufar.exception.OrderNotFoundException;
import com.zufar.repository.OrderRepository;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collection;
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
    private static final Long invalidOrderId = 343L;
    private static final Long clientId = 1L;
    private static final Long categoryId = 1L;
    private static final Long invalidCategoryId = 3435L;
    private static final List<Order> orders = new ArrayList<>();
    private static final Set<Long> orderIds = new HashSet<>();
    private static final Category category = new Category(categoryId, "categoryName2");
    private static final Order orderEntity = new Order(1L, "goodsName1", category, clientId);
    private static final OrderDTO orderDTO = new OrderDTO(orderId, "goodsName1", categoryId, clientId);


    @BeforeClass
    public static void setUp() {
        orderIds.add(orderId);
        orderIds.add(2L);
        orders.add(orderEntity);
        orders.add(new Order(2L, "goodsName1", category, clientId));
    }

    @Test
    public void whenGetAllCalledThenCollectionShouldBeReturned() {
        when(orderRepository.findAll()).thenReturn(orders);

        Collection<Order> expected = orders;
        Collection<Order> actual = this.orderService.getAll();

        verify(orderRepository, times(1)).findAll();
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void whenGetAllByClientIdCalledThenCollectionShouldBeReturned() {
        when(orderRepository.findAllByClientId(clientId)).thenReturn(orders);

        Collection<Order> expected = orders;
        Collection<Order> actual = this.orderService.getAllByClientId(clientId);

        verify(orderRepository, times(1)).findAllByClientId(clientId);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void whenGetAllByIdsCalledThenCollectionShouldBeReturned() {
        when(orderRepository.findAllById(orderIds)).thenReturn(orders);

        Collection<Order> expected = orders;
        Collection<Order> actual = this.orderService.getAllByIds(orderIds);

        verify(orderRepository, times(1)).findAllById(orderIds);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void whenGetByIdCalledThenOrderShouldBeReturned() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
        when(orderRepository.existsById(orderId)).thenReturn(true);


        Order expected = orderEntity;
        Order actual = this.orderService.getById(orderId);

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).existsById(orderId);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test(expected = OrderNotFoundException.class)
    public void whenGetByIdWithInvalidIdThenOrderNotFoundExceptionShouldThrow() {
        when(orderRepository.existsById(invalidOrderId)).thenReturn(false);

        orderService.getById(invalidOrderId);
    }

    @Test
    public void whenSaveCalledThenOrderShouldBeReturned() {
        final Order order = OrderServiceTest.orderEntity;
        order.setId(null);
        when(orderRepository.save(order)).thenReturn(OrderServiceTest.orderEntity);
        when(categoryService.getById(categoryId)).thenReturn(category);

        Order expected = OrderServiceTest.orderEntity;

        final OrderDTO orderDTO = OrderServiceTest.orderDTO;
        orderDTO.setId(null);
        Order actual = this.orderService.save(orderDTO);

        verify(orderRepository, times(1)).save(orderEntity);
        verify(categoryService, times(1)).getById(categoryId);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void whenUpdateCalledThenOrderShouldBeReturned() {
        when(orderRepository.existsById(orderId)).thenReturn(true);
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        when(categoryService.getById(categoryId)).thenReturn(category);

        Order expected = orderEntity;
        Order actual = this.orderService.update(orderDTO);

        verify(orderRepository, times(1)).save(orderEntity);
        verify(orderRepository, times(1)).existsById(orderId);
        verify(categoryService, times(1)).getById(categoryId);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test(expected = OrderNotFoundException.class)
    public void whenUpdateOrderWithInvalidIdThenOrderNotFoundExceptionShouldThrow() {
        OrderDTO noExistedOrder = orderDTO;
        noExistedOrder.setId(invalidOrderId);
        when(orderRepository.existsById(noExistedOrder.getId())).thenReturn(false);

        orderService.update(noExistedOrder);
    }

    @Test
    public void whenDeleteByIdCalled() {
        when(orderRepository.existsById(orderId)).thenReturn(true);
        doNothing().when(orderRepository).deleteById(orderId);

        orderService.deleteById(orderId);

        verify(orderRepository, times(1)).existsById(orderId);
        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test(expected = OrderNotFoundException.class)
    public void whenDeleteOrderWithInvalidIdThenOrderNotFoundExceptionShouldThrow() {
        when(orderRepository.existsById(invalidOrderId)).thenReturn(false);

        orderService.getById(invalidOrderId);
    }

    @Test
    public void whenDeleteAllByClientCalled() {
        doNothing().when(orderRepository).deleteAllByClientId(clientId);

        orderService.deleteAllByClientId(clientId);

        verify(orderRepository, times(1)).deleteAllByClientId(clientId);
    }
}
