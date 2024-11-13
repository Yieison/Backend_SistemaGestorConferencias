package com.example.demo.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Articulo;
import com.example.demo.model.Evento;
import com.example.demo.model.Presentacion;
import com.example.demo.model.Sala;
import com.example.demo.model.Sesion;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ArticuloRepository;
import com.example.demo.repository.EventoRepository;
import com.example.demo.repository.PresentacionRepository;
import com.example.demo.repository.SesionRepository;

@Service
public class PresentacionService {
	
	@Autowired
	PresentacionRepository presentacionRepository;
	
	@Autowired
	SesionRepository sesionRepository;
	
	@Autowired
	ArticuloRepository articuloRepository;
	
	@Autowired
	EventoRepository eventoRepository;
	
	public List<Presentacion> getPresentaciones(){
		return presentacionRepository.findAll();
	}
	
	public List<Presentacion> getPresentacionesSesion(int idSesion){
      return presentacionRepository.findByPresentaciones(idSesion);
	}
	
	//traer estudiante por id
		public Optional<Presentacion> getPresentacion(Integer id) {
			return presentacionRepository.findById(id);
		}
		
		public List<Presentacion> getConferencia(int id){
			return presentacionRepository.findByConferencia(id);
		}
		
		public List<Presentacion> getPresentacionesUsuario(int idUsuario){
			return presentacionRepository.findByPresentacionesUsuario(idUsuario);
		}
		
		//agregar presentacion de un articulo
		public void Guardar(Presentacion presentacion, int idArticulo, int idSesion) {
		    Optional<Articulo> articuloOpt = articuloRepository.findById(idArticulo);
		    Sesion sesion = sesionRepository.findById(idSesion)
		            .orElseThrow(() -> new RuntimeException("sesion no encontrada"));
		    
		    // Validar que la hora de la presentación está dentro del rango de la sesión
		    if (!isWithinSessionTime(sesion, presentacion)) {
		        throw new RuntimeException("La presentación está fuera del horario de la sesión.");
		    }

		    // Validar que no se solapen las presentaciones y eventos
		    if (hasOverlapWithOtherPresentations(sesion, presentacion) || hasOverlapWithEvents(sesion, presentacion)) {
		        throw new RuntimeException("La presentación se cruza con otra presentación o evento en la sesión.");
		    }

		    presentacion.setFecha_presentacion(sesion.getFechaDia());
		    if (articuloOpt.isPresent()) {
		        Articulo articuloPresentar = articuloOpt.get();
		        presentacion.setArticulo(articuloPresentar);
		        articuloPresentar.setEstado("PRESENTACION");
		        presentacion.setSesion(sesion);
		        presentacionRepository.save(presentacion);
		    }
		}

		// Método para validar que la presentación está dentro del rango de la sesión
		private boolean isWithinSessionTime(Sesion sesion, Presentacion presentacion) {
		    return !presentacion.getHoraInicio().isBefore(sesion.getHoraInicio()) &&
		           !presentacion.getHoraFin().isAfter(sesion.getHoraFin());
		}

		// Método para verificar solapamientos entre presentaciones en la sesión
		private boolean hasOverlapWithOtherPresentations(Sesion sesion, Presentacion presentacion) {
		    List<Presentacion> presentaciones = presentacionRepository.findByPresentaciones(sesion.getId());

		    for (Presentacion p : presentaciones) {
		        if (doTimesOverlap(p.getHoraInicio(), p.getHoraFin(), presentacion.getHoraInicio(), presentacion.getHoraFin())) {
		            return true;
		        }
		    }
		    return false;
		}

		// Método para verificar solapamientos con eventos en la sesión
		private boolean hasOverlapWithEvents(Sesion sesion, Presentacion presentacion) {
		    List<Evento> eventos = eventoRepository.findBySesion(sesion.getId());

		    for (Evento evento : eventos) {
		        if (doTimesOverlap(evento.getHoraInicio(), evento.getHoraFin(), presentacion.getHoraInicio(), presentacion.getHoraFin())) {
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
