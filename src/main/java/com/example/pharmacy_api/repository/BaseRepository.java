package com.example.pharmacy_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T , ID extends Number> extends JpaRepository<T, ID> {
}
