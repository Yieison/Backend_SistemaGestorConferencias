package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Conferencia;
import com.example.demo.model.Topico;


@Repository
public interface TopicoRepository extends JpaRepository<Topico,Integer> {
	
	 @Query("SELECT t FROM Topico t WHERE t.conferencia = ?1")
	  List<Topico> findByConferencia(Conferencia conferencia);

}
