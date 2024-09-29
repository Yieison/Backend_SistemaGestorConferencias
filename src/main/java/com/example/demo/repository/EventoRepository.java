package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento,Integer> {
	
	@Query("SELECT p FROM Evento p WHERE p.sesion.id = ?1")
	List<Evento> findBySesion(int idSesion);

}
