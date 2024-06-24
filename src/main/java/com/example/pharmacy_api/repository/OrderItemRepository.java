package com.example.pharmacy_api.repository;

import com.example.pharmacy_api.model.entity.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends BaseRepository<OrderItem, Integer> {

    List<OrderItem> findOrderItemByOrder_Id(int id);
}
