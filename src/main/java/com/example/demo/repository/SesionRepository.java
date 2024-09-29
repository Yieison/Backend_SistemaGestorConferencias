package com.example.demo.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Sala;
import com.example.demo.model.Sesion;

@Repository
public interface SesionRepository extends JpaRepository<Sesion,Integer> {
	//Buscar las sesiones por fecha
	@Query("SELECT s FROM Sesion s WHERE s.sala = :sala AND s.fechaDia = :fechaDia")
	List<Sesion> findBySalaAndFechaDia(@Param("sala") Sala sala, @Param("fechaDia") Date fechaDia);
	
	// JPQL para obtener sesiones ordenadas por la hora de inicio
    @Query("SELECT s FROM Sesion s ORDER BY s.horaInicio ASC")
    List<Sesion> findAllOrderByHoraInicioAsc();

}
