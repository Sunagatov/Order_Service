package com.zufar.order_service_api.client;


import com.zufar.dto.OrderDTO;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "order-service", path = "orders")
public interface OrderClientService {

    @PostMapping(value = "clients")
    @ResponseBody ResponseEntity<List<OrderDTO>> getAllByClientIds(@RequestBody Long... clientIds);

    @DeleteMapping(value = "client/{clientId}")
    @ResponseBody ResponseEntity deleteAllByClientId(@PathVariable(value = "clientId") Long clientId);
}
