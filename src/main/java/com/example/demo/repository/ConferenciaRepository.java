package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Conferencia;



@Repository
public interface ConferenciaRepository extends JpaRepository<Conferencia,Integer> {
	
	@Query("SELECT c FROM Conferencia c WHERE c.chair.id = :id")
	List<Conferencia> findByChair(Integer id);

}
