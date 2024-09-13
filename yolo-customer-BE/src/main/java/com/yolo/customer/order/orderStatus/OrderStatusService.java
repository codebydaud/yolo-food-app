package com.yolo.customer.order.orderStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusService {

    private final OrderStatusRepository orderStatusRepository;

    @Autowired
    public OrderStatusService(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    public Integer findStatusIdByValue(String value) {
        OrderStatus orderStatus = orderStatusRepository.findByValue(value)
                .orElseThrow(() -> new RuntimeException("Order status '" + value + "' not found"));
        return orderStatus.getId();
    }
}