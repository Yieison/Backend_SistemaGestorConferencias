package com.example.demo.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
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
@Table(name = "sala")
public class Sala implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nombre;
	
	private String tipo;
	
	@Column(name = "url_sala", length = 800) 
	private String urlSala;
	
	@OneToMany(mappedBy = "sala")
	@JsonIgnore
	private List<Sesion> sesiones;
	
	@ManyToOne
	@JoinColumn(name = "institucion_id")
	private Institucion institucion;

}
