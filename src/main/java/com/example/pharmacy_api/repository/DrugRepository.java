package com.example.pharmacy_api.repository;

import com.example.pharmacy_api.model.entity.Drug;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugRepository extends BaseRepository<Drug, Integer> {

    List<Drug> findAllByCategory_Id(int id);

    List<Drug> findAllByCategory_Admin_Id(int adminId);

}
