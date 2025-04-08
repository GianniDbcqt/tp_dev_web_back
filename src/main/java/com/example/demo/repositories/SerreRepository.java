package com.example.demo.repositories;

import com.example.demo.models.Serre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerreRepository extends JpaRepository<Serre, Long> {
}
