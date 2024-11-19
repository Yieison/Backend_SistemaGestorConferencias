package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Comite;


@Repository
public interface ComiteRepository extends JpaRepository<Comite,Integer> {

	@Query("SELECT e FROM Comite e WHERE e.conferencia.id_conferencia = :idConferencia")
    List<Comite> findByConferencia(@Param("idConferencia") int idConferencia);

}
