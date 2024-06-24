package com.example.pharmacy_api.repository;

import com.example.pharmacy_api.model.entity.Favorite;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends BaseRepository<Favorite, Integer> {

    List<Favorite> findAllByUser_Id(int id);

    List<Favorite> findAllByDrug_Id(int id);

    void deleteFavoriteByUserIdAndDrugId(int drugId,int userId);
}
