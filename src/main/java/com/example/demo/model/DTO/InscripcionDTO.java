package com.example.demo.model.DTO;

import java.sql.Date;
import java.time.LocalDate;

import com.example.demo.model.Conferencia;
import com.example.demo.model.Inscripcion;
import com.example.demo.model.Usuario;

import lombok.Data;

@Data
public class InscripcionDTO {
	
	public InscripcionDTO(Inscripcion inscripcion) {
        this.id = inscripcion.getId();
        this.estado = inscripcion.getEstado();
        this.fechaInscripcion = inscripcion.getFechaInscripcion();
        
        // Convertir Usuario y Conferencia a sus respectivos DTOs
        this.asistente = inscripcion.getAsistente();
        this.conferencia = inscripcion.getConferencia();
    }
	
	private int id;
	 private String estado;
	 private Usuario asistente;
	 private Conferencia conferencia;
	 private Date fechaInscripcion;
	    
}
