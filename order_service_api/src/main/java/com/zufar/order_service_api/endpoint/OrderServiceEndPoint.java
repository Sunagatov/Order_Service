package com.zufar.order_service_api.endpoint;

import com.zufar.dto.OrderDTO;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@RequestMapping("/default")
public interface OrderServiceEndPoint<E extends OrderDTO> {

    /**
     * Returns all orders.
     *
     * @return all orders.
     */
    List<E> getAll();

    /**
     * Returns all orders with given client ids.
     *
     * @param clientIds must not be {@literal null}.
     * @return the orders with the given client ids.
     */
    List<E> getAllByClientIds(Long... clientIds);

    /**
     * Returns the order with given client id.
     *
     * @param id must not be {@literal null}.
     * @return the order with the given id.
     */
    ResponseEntity<E> getById(Long id);

    /**
     * Deletes the order with the given id.
     *
     * @param id must not be {@literal null}.
     * @return the operation info.
     */
    ResponseEntity deleteById(Long id);

    /**
     * Deletes all orders with the given client id.
     *
     * @param clientId must not be {@literal null}.
     * @return the operation info.
     */
    ResponseEntity deleteAllByClientId(Long clientId);

    /**
     * Saves the given order.
     *
     * @param order must not be {@literal null}.
     * @return the operation info.
     */
    ResponseEntity save(@NotNull @Valid E order);

    /**
     * Updates the given order.
     *
     * @param order must not be {@literal null}.
     * @return the operation info.
     */
    ResponseEntity update(@NotNull @Valid E order);
}
