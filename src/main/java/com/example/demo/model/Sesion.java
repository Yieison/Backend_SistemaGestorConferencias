package com.example.demo.model;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="sesion")
public class Sesion implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nombre;
	private Date fechaDia;
	private LocalTime horaInicio;
	private LocalTime horaFin;
	
	@OneToMany(mappedBy = "sesion")
	@JsonIgnore
	private List<Presentacion> presentaciones;
	
	@ManyToOne
	@JoinColumn(name = "id_conferencia")
	private Conferencia conferencia;
	
	
	@OneToMany(mappedBy = "sesion")
	private List<Evento> eventos;
	
	@ManyToOne
	@JoinColumn(name = "sala_id")
	private Sala sala;
	
	

}
