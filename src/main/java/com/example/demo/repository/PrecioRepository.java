package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Precio;

@Repository
public interface PrecioRepository extends JpaRepository<Precio,Integer> {

}
