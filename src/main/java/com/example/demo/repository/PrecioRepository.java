package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Evaluacion;
import com.example.demo.model.Precio;

@Repository
public interface PrecioRepository extends JpaRepository<Precio,Integer> {
	
	@Query("SELECT e FROM Precio e WHERE e.conferencia.id_conferencia = :idConferencia")
    List<Precio> findByConferencia(@Param("idConferencia") int idConferencia);

}
