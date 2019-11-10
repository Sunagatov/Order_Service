package com.zufar.order_service_api.client;

import java.util.List;

import com.zufar.order_management_system_common.dto.OrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "order-service", path = "orders")
public interface OrderFeignService {

    /**
     * Returns all orders with given client ids.
     *
     * @param clientIds must not be {@literal null}.
     * @return the orders with the given client ids.
     */
    @PostMapping(value = "clients")
    @ResponseBody ResponseEntity<List<OrderDTO>> getAllByClientIds(@RequestBody Long... clientIds);

    /**
     * Deletes all orders with the given client id.
     *
     * @param clientId must not be {@literal null}.
     * @return the operation info.
     */
    @DeleteMapping(value = "client/{clientId}")
    @ResponseBody ResponseEntity deleteAllByClientId(@PathVariable(value = "clientId") Long clientId);
}
