package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Sala;

@Repository
public interface SalaRepository extends JpaRepository<Sala,Integer> {
	
	

}
