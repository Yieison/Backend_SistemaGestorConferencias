package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Articulo;
import com.example.demo.model.Usuario;


@Repository
public interface ArticuloRepository extends JpaRepository<Articulo,Integer>{
	
	@Query("SELECT a FROM Articulo a WHERE a.estado = ?1")
    List<Articulo> findByEstado(String estado);
	
	 @Query("SELECT a FROM Articulo a WHERE a.autor.id_usuarios = :idAutor")
	 List<Articulo> findArticulosByAutorId(@Param("idAutor") int idAutor);
	 
	 @Query("SELECT a FROM Articulo a WHERE a.conferencia.id_conferencia = :idConferencia")
	 List<Articulo> findArticulosByConferencia(@Param("idConferencia") int idConferencia);
}
