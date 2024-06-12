package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name ="topico")
public class Topico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_topico;
	
	private String tema;
	
	
	@ManyToOne
	@JoinColumn(name = "conferencia_id")
	@JsonIgnoreProperties("topicos") // Ignorar la serialización de la lista de topicos dentro de la conferencia
	private Conferencia conferencia;

}
