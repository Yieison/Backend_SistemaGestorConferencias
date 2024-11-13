package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Presentacion;

@Repository
public interface PresentacionRepository extends JpaRepository<Presentacion,Integer> {
	@Query("SELECT p FROM Presentacion p WHERE p.sesion.id = ?1")
    List<Presentacion> findByPresentaciones(int id_sesion);
	
	@Query("SELECT e FROM Presentacion e WHERE e.sesion.conferencia.id_conferencia = :idConferencia")
	List<Presentacion> findByConferencia(@Param("idConferencia")Integer idConferencia);
	
	@Query("SELECT p FROM Presentacion p WHERE p.articulo.autor.id_usuarios = :idUsuario")
    List<Presentacion> findByPresentacionesUsuario(int idUsuario);
	
}
