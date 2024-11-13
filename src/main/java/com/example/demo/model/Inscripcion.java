package com.example.demo.model;

import java.io.Serializable;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "inscripcion")


@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"pago"})
public class Inscripcion implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String estado;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "asistente_id")
	@JsonManagedReference // Evita la serialización recursiva del usuario
    private Usuario asistente;

    // Relación con Conferencia
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "conferencia_id")
    @JsonIgnoreProperties("comites")
    private Conferencia conferencia;
    
    private Date fechaInscripcion;
    
    
    @OneToOne(mappedBy = "inscripcion", cascade = CascadeType.ALL)
    private Pago pago;
	
	

}
