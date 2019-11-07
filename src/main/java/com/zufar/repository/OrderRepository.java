package com.zufar.repository;

import com.zufar.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    boolean existsByClientId(Long clientId);
    Iterable<Order> findAllByClientId(Long clientId);
    Iterable<Order> findAllByClientIdIn(List<Long> clientIds);
    void deleteAllByClientId(Long clientId);
}
