package com.zufar.repository;

import com.zufar.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    void deleteAllByClientId(Long clientId);
    Iterable<Order> findAllByClientId(Long clientId);
}
