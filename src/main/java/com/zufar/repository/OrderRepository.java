package com.zufar.repository;

import com.zufar.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    
    boolean existsByClientId(Long clientId);

    Iterable<Order> findAllByClientIdIn(Long... clientIds);

    void deleteAllByClientId(Long clientId);
}
