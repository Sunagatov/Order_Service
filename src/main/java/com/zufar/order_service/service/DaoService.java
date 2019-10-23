package com.zufar.order_service.service;

import java.util.Collection;

public interface DaoService<T> {
    
    Collection<T> getAll(Iterable<Long> ids);
    T getById(Long id);
    T save(Object entity);
    T update(Object entity);
    void deleteById(Long id) ;
    void deleteAll(Iterable<Long> ids);
}
