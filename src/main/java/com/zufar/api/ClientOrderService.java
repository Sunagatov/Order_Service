package com.zufar.api;


import com.zufar.dto.OrderDTO;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "order-service", path = "orders")
public interface ClientOrderService {

    @PostMapping(value = "clients")
    @ResponseBody ResponseEntity<List<OrderDTO>> getAllByClientIds(@RequestBody Long... clientIds);

    @DeleteMapping(value = "client/{clientId}")
    @ResponseBody ResponseEntity deleteAllByClientId(@PathVariable Long clientId);
}
