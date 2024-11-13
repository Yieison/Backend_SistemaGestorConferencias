package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Articulo;
import com.example.demo.model.Inscripcion;


@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion,Integer> {
	
	@Query("SELECT a FROM Inscripcion a WHERE a.estado = ?1")
    List<Inscripcion> findByEstado(String estado);
	
	@Query("SELECT i FROM Inscripcion i JOIN FETCH i.asistente JOIN FETCH i.conferencia WHERE i.asistente.id = :idUsuario")
	List<Inscripcion> findInscripcionesUsuario(@Param("idUsuario") int idUsuario);

	
	

}

