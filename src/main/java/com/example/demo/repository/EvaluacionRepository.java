package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Evaluacion;


@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion,Integer>{
	
	List<Evaluacion> findByEstado(String estado);
	
	@Query("SELECT e FROM Evaluacion e WHERE e.articulo.id_articulo = :idArticulo")
    List<Evaluacion> findByArticuloId(@Param("idArticulo") int idArticulo);
	
	@Query("SELECT e FROM Evaluacion e WHERE e.articulo.conferencia.id_conferencia = :idConferencia")
    List<Evaluacion> findByConferencia(@Param("idConferencia") int idConferencia);
	
}
