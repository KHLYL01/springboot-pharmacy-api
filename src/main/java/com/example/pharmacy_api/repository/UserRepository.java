package com.example.pharmacy_api.repository;

import com.example.pharmacy_api.model.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Integer> {
    Optional<User> findByEmail(String email);

}
