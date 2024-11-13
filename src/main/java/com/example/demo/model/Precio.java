package com.example.demo.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "precio")
public class Precio implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private BigDecimal monto;
	
	 private String tipoUsuario; // e.g., "ESTUDIANTE", "PROFESIONAL", "AUTOR"
	 
	 
	 //private String etapa; // e.g., "INSCRIPCION_TEMPRANA", "INSCRIPCION_TARDIA"
	 
	 

	 @ManyToOne
	 @JoinColumn(name = "id_conferencia")
	 private Conferencia conferencia;

}
