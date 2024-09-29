package com.example.demo.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="presentacion")
public class Presentacion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_presentacion;
	private String titulo;
	private String resumen;
	private String palabras_clave;
	private int Duracion;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fecha_presentacion;
	
	 @ManyToOne
	 @JoinColumn(name = "id_articulo")
	 private Articulo articulo;
	 
	 private LocalTime horaInicio;
	 private LocalTime horaFin;
	 
	 @ManyToOne
	 @JoinColumn(name = "id_sesion")
	 private Sesion sesion;
	

}
