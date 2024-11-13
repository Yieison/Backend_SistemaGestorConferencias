package com.example.demo.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Evento;
import com.example.demo.model.Presentacion;
import com.example.demo.model.Sesion;
import com.example.demo.repository.EventoRepository;
import com.example.demo.repository.PresentacionRepository;
import com.example.demo.repository.SesionRepository;

@Service
public class EventoService {
	
	
	@Autowired
	EventoRepository eventoRepository;
	
	@Autowired
	SesionRepository sesionRepository;
	
	
	@Autowired
	PresentacionRepository presentacionRepository;
	
	public List<Evento> getEventos(){
		return eventoRepository.findAll();
	}
	
	public List<Evento> getEventosSesion(int idSesion){
		return eventoRepository.findBySesion(idSesion);
	}
	
	public void agregarEvento(int idSesion, Evento evento) {
	    Sesion sesion = sesionRepository.findById(idSesion)
	            .orElseThrow(() -> new RuntimeException("Sesion no encontrada"));

	    // Validar que el evento está dentro del horario de la sesión
	    if (!isWithinSessionTime(sesion, evento)) {
	        throw new RuntimeException("El evento está fuera del horario de la sesión.");
	    }

	    // Validar que el evento no se solape con otros eventos o presentaciones en la sesión
	    if (hasOverlapWithOtherEvents(sesion, evento) || hasOverlapWithPresentations(sesion, evento)) {
	        throw new RuntimeException("El evento se solapa con otra presentación o evento en la sesión.");
	    }

	    evento.setSesion(sesion);
	    eventoRepository.save(evento);
	}

	// Método para validar que el evento está dentro del rango de la sesión
	private boolean isWithinSessionTime(Sesion sesion, Evento evento) {
	    return !evento.getHoraInicio().isBefore(sesion.getHoraInicio()) &&
	           !evento.getHoraFin().isAfter(sesion.getHoraFin());
	}

	// Método para verificar solapamientos entre eventos en la sesión
	private boolean hasOverlapWithOtherEvents(Sesion sesion, Evento evento) {
	    List<Evento> eventos = eventoRepository.findBySesion(sesion.getId());

	    for (Evento e : eventos) {
	        if (doTimesOverlap(e.getHoraInicio(), e.getHoraFin(), evento.getHoraInicio(), evento.getHoraFin())) {
	            return true;
	        }
	    }
	    return false;
	}

	// Método para verificar solapamientos con presentaciones en la sesión
	private boolean hasOverlapWithPresentations(Sesion sesion, Evento evento) {
	    List<Presentacion> presentaciones = presentacionRepository.findByPresentaciones(sesion.getId());

	    for (Presentacion p : presentaciones) {
	        if (doTimesOverlap(p.getHoraInicio(), p.getHoraFin(), evento.getHoraInicio(), evento.getHoraFin())) {
	            return true;
	        }
	    }
	    return false;
	}

	// Método genérico para verificar si dos intervalos de tiempo se solapan
	private boolean doTimesOverlap(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
	    return (start1.isBefore(end2) && end1.isAfter(start2));
	}

}
