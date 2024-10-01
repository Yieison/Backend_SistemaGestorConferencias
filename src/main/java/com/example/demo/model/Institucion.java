package com.example.demo.model;

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
@Table(name = "institucion")
public class Institucion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nombreInstitucion;
	
	@OneToMany(mappedBy = "institucion")
	@JsonIgnore
	private List<Usuario> usuario;
	
	@ManyToOne
	@JoinColumn(name ="id_ciudad")
	@JsonIgnore
	private Ciudad ciudad;
	
	@OneToMany(mappedBy = "institucion")
	@JsonIgnore
	private List<Sala> salas;

}
