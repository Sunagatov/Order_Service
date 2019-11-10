package com.zufar.order_service_api.endpoint;


import java.util.List;

import com.zufar.order_management_system_common.dto.OrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@RequestMapping("/default")
public interface OrderServiceEndPoint<E extends OrderDTO, T extends Number> {

    /**
     * Returns all orders.
     *
     * @return all orders.
     */
    ResponseEntity<List<E>> getAll();

    /**
     * Returns all orders with given client ids.
     *
     * @param clientIds must not be {@literal null}.
     * @return the orders with the given client ids.
     */
    ResponseEntity<List<E>> getAllByClientIds(T... clientIds);

    /**
     * Returns the order with given client id.
     *
     * @param id must not be {@literal null}.
     * @return the order with the given id.
     */
    ResponseEntity<E> getById(T  id);


    /**
     * Deletes the order with the given id.
     *
     * @param id must not be {@literal null}.
     * @return the operation info.
     */
    ResponseEntity deleteById(T id);

    /**
     * Deletes all orders with the given client id.
     *
     * @param clientId must not be {@literal null}.
     * @return the operation info.
     */
    ResponseEntity deleteAllByClientId(T clientId);

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
