package com.example.demo.model;

import java.io.Serializable;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "evento")
public class Evento implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String tipo;
	
	private String descripcion;
	
	private LocalTime horaInicio;
	private LocalTime horaFin;
	
	@ManyToOne
	@JoinColumn(name = "id_sesion")
	@JsonIgnore
	private Sesion sesion;
	
	

}
