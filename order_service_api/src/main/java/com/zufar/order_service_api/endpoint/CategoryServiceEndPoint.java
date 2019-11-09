package com.zufar.order_service_api.endpoint;

import com.zufar.dto.Category;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/default")
public interface CategoryServiceEndPoint<E extends Category> {

    /**
     * Returns all order categories
     *
     * @return all categories
     */
    ResponseEntity<E[]> getCategories();
}
