package com.example.pharmacy_api.repository;

import com.example.pharmacy_api.model.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Integer> {

    List<Category> findAllByAdmin_Id(int id);
}
