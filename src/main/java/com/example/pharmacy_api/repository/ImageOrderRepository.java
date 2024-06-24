package com.example.pharmacy_api.repository;

import com.example.pharmacy_api.model.entity.Admin;
import com.example.pharmacy_api.model.entity.ImageOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageOrderRepository extends BaseRepository<ImageOrder, Integer> {
    ImageOrder findTopByName(String name);
}
