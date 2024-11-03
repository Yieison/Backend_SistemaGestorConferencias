package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Conferencia;



@Repository
public interface ConferenciaRepository extends JpaRepository<Conferencia,Integer> {
	
	@Query("SELECT c FROM Conferencia c WHERE c.chair.id = :id")
	List<Conferencia> findByChair(Integer id);
	
	@Query("SELECT c FROM Conferencia c WHERE c.estado = :estado")
	List<Conferencia> findByEstado(String estado);
	
	@Query("SELECT c FROM Conferencia c WHERE c.Fecha_fin <= :fechaFin AND c.estado = 'Activa'")
	List<Conferencia> findByFechaFinAfterAndActivoTrue(@Param("fechaFin") LocalDate fechaFin);



}
