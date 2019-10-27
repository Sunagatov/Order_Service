package com.zufar.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "client-service", path = "clients")
public interface ClientService {

    @PostMapping(value = "client")
    @ResponseBody ResponseEntity<Boolean> isClientExists(@RequestBody Long clientId);
}
