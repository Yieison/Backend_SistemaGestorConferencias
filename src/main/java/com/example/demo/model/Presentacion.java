package com.example.demo.model;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Presentacion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_presentacion;
	private String titulo;
	private String resumen;
	private String palabras_clave;
	private int Duracion;
	private Timestamp fecha_presentacion;
	
	 @ManyToOne
	 @JoinColumn(name = "id_articulo")
	 private Articulo articulo;
	

}
