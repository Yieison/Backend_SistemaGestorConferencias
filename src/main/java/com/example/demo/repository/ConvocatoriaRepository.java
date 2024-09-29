package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Convocatoria;

@Repository
public interface ConvocatoriaRepository extends JpaRepository<Convocatoria,Integer> {
	

}
