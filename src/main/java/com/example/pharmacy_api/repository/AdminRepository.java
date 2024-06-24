package com.example.pharmacy_api.repository;

import com.example.pharmacy_api.model.entity.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends BaseRepository<Admin, Integer> {
    Admin findAdminByUser_Id(int userId);

    Admin findAdminByUser_Email(String email);

    List<Admin> findAllByRegion(String region);
}
