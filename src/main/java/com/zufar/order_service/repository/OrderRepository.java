package com.zufar.order_service.repository;

import com.zufar.order_service.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    
    boolean existsByClientId(Long clientId);

    Iterable<Order> findAllByClientIdIn(Long... clientIds);

    void deleteAllByClientId(Long clientId);
}
