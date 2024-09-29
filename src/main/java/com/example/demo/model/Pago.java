package com.example.demo.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "pago")
public class Pago implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int monto;
	
	private String metodoPago;
	private String estado_pago;
	
	@OneToOne
	@JoinColumn(name = "inscripcion_id", referencedColumnName = "id")
	private Inscripcion inscripcion;
	
	private String urlSoporte;
	

}
