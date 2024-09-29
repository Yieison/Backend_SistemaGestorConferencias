package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Articulo;
import com.example.demo.model.Inscripcion;


@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion,Integer> {
	
	@Query("SELECT a FROM Inscripcion a WHERE a.estado = ?1")
    List<Inscripcion> findByEstado(String estado);

}

