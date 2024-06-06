package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Evaluacion;


@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion,Integer>{
	
	List<Evaluacion> findByEstado(String estado);
}
