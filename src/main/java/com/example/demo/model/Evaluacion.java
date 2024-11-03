package com.example.demo.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "evaluacion")
public class Evaluacion implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp fechaHora;
	
	@ManyToOne
	@JoinColumn(name = "articulo_id_articulo")
	private Articulo articulo;

	@ManyToOne
    @JoinColumn(name = "evaluador_id")
    private Usuario evaluador;
	
	private String estado;
	
	private String comentario;
	

}
