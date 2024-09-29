package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago,Integer> {
	@Query("SELECT a FROM Pago a WHERE a.estado_pago = ?1")
	List<Pago> findPagoByEstado (String estado_pago);
}
