package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="evaluador")
public class Evaluador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_evaluador;
	private String nombre;
	private String apellido;
	
	

}
