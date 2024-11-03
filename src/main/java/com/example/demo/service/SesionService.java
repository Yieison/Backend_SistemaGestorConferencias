package com.example.demo.service;

import java.sql.Date;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Conferencia;
import com.example.demo.model.Sala;
import com.example.demo.model.Sesion;
import com.example.demo.repository.ConferenciaRepository;
import com.example.demo.repository.SalaRepository;
import com.example.demo.repository.SesionRepository;

@Service
public class SesionService {
	
	@Autowired
	SesionRepository sesionRepository;
	
	@Autowired
	ConferenciaRepository conferenciaRepository;
	
	@Autowired
	SalaRepository salaRepository;
	
	public List<Sesion> getAllSesiones(){
		return sesionRepository.findAll();
	}
	
	public List<Sesion> getSesionesConferencia(int idConferencia){
		Conferencia conferencia = conferenciaRepository.findById(idConferencia).orElseThrow(() -> new RuntimeException("Conferencia no encontrada"));
		return conferencia.getSesiones();
	}
	
	
	public List<Sesion> listarSesionesOrdenadasPorHora() {
        return sesionRepository.findAllOrderByHoraInicioAsc();
    }
	
	
	public void agregarSesion(int idConferencia, Sesion sesion, int idSala) {
	    // Buscar la conferencia por id
	    Conferencia conferencia = conferenciaRepository.findById(idConferencia)
	        .orElseThrow(() -> new RuntimeException("Conferencia no encontrada"));

	    // Buscar la sala por id
	    Sala sala = salaRepository.findById(idSala)
	        .orElseThrow(() -> new RuntimeException("Sala no encontrada"));

	    // Verificar si la sala está ocupada en el rango de fecha y hora
	    boolean salaDisponible = verificarDisponibilidadSala(sala, sesion.getFechaDia(), sesion.getHoraInicio(), sesion.getHoraFin());

	    if (salaDisponible) {
	        // Asignar la conferencia y la sala a la sesión
	        sesion.setConferencia(conferencia);
	        sesion.setSala(sala);
	        // Guardar la sesión
	        sesionRepository.save(sesion);
	        // Aquí no cambiamos el estado de la sala a "OCUPADA" permanentemente
	        // ya que la disponibilidad se verifica por fecha y hora.
	    } else {
	        throw new RuntimeException("La sala seleccionada se encuentra ocupada en el horario solicitado");
	    }
	}

	
	
	public boolean verificarDisponibilidadSala(Sala sala, Date fechaDia, LocalTime horaInicio, LocalTime horaFin) {
	    List<Sesion> sesionesEnSala = sesionRepository.findBySalaAndFechaDia(sala, fechaDia);

	    for (Sesion sesionExistente : sesionesEnSala) {
	        // Verificar si hay superposición de horarios
	        if ((sesionExistente.getHoraInicio().isBefore(horaFin) && sesionExistente.getHoraFin().isAfter(horaInicio))) {
	            return false; // La sala está ocupada en el horario solicitado
	        }
	    }

	    return true; // La sala está disponible
	}
	
	
	public Sesion editarSesion(int idSesion,Sesion sesionActualizada) {
	    Sesion sesion = sesionRepository.findById(idSesion)
		        .orElseThrow(() -> new RuntimeException("Sesion no encontrada"));
	    sesion.setNombre(sesionActualizada.getNombre());
	    sesion.setFechaDia(sesionActualizada.getFechaDia());
	    sesion.setHoraInicio(sesionActualizada.getHoraInicio());
	    sesion.setHoraFin(sesionActualizada.getHoraFin());
	    return sesionRepository.save(sesion);
	}

	
	

}
