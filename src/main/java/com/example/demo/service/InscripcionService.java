package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Conferencia;
import com.example.demo.model.Inscripcion;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ConferenciaRepository;
import com.example.demo.repository.InscripcionRepository;
import com.example.demo.repository.UsuarioRepository;

@Service
public class InscripcionService {
	
	@Autowired
	InscripcionRepository inscripcionRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	ConferenciaRepository conferenciaRepository;
	
	
	
	
	public List<Inscripcion> getAllInscripciones() {
		return inscripcionRepository.findAll();
	}
	
	public void registrarInscripcion(int idUsuario,int idConferencia,Inscripcion inscripcion) {
		    Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
	        Conferencia conferencia = conferenciaRepository.findById(idConferencia).orElseThrow(() -> new RuntimeException("Conferencia no encontrada"));
	        inscripcion.setAsistente(usuario);
	        inscripcion.setConferencia(conferencia);
	        inscripcionRepository.save(inscripcion);
	        
	}
	
	public List<Inscripcion> getInscripcionesEstado(String estado){
		return inscripcionRepository.findByEstado(estado);
	}
	
	public List<Inscripcion> getInscripcionesUsuario(int idUsaurio){
		return inscripcionRepository.findInscripcionesUsuario(idUsaurio);
	}
	
	
	public Inscripcion cambiarEstadoInscripcion(int idInscripcion,String estado){
		Inscripcion inscripcion = inscripcionRepository.findById(idInscripcion).orElseThrow(() -> new RuntimeException("Inscripcion no encontrada"));
		inscripcion.setEstado(estado);
		return inscripcionRepository.save(inscripcion);
	}

}
