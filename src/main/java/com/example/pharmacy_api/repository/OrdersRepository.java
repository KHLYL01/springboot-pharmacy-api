package com.example.pharmacy_api.repository;

import com.example.pharmacy_api.model.entity.Orders;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends BaseRepository<Orders, Integer> {

    List<Orders> getAllByAdmin_Id(int adminId);

    List<Orders> getAllByUser_Id(int userId);


}
