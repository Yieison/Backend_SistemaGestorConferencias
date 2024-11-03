package com.example.demo.model;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table
@ToString
public class Convocatoria implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String descripcion;
	
	private LocalDate fechaInicioEnvio;

	private LocalDate fechaLimiteEnvio;

	private LocalDate fechaAceptacion;

	private LocalDate fechaPublicacion;

	private LocalDate fechaEnvioPublicaciones;
	
	
	 @ManyToOne
	 @JoinColumn(name = "id_conferencia") // Relación con la conferencia
	 @ToString.Exclude
	 private Conferencia conferencia;
	
	

}
