package com.zufar.order_service_impl.converter;

import com.zufar.order_management_system_common.dto.OrderDTO;
import com.zufar.order_service_impl.entity.Order;

public class OrderConverter {

    public static Order convertToOrder(OrderDTO orderDTO) {
        return new Order(
                orderDTO.getId(),
                orderDTO.getGoodsName(),
                orderDTO.getCategory(),
                orderDTO.getClientId()
        );
    }

    public static OrderDTO convertToOrderDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getGoodsName(),
                order.getCategory(),
                order.getClientId()
        );
    }
}
