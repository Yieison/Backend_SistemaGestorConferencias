package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Evento;
import com.example.demo.model.Sesion;
import com.example.demo.repository.EventoRepository;
import com.example.demo.repository.SesionRepository;

@Service
public class EventoService {
	
	
	@Autowired
	EventoRepository eventoRepository;
	
	@Autowired
	SesionRepository sesionRepository;
	
	public List<Evento> getEventos(){
		return eventoRepository.findAll();
	}
	
	public List<Evento> getEventosSesion(int idSesion){
		return eventoRepository.findBySesion(idSesion);
	}
	
	public void agregarEvento(int idSesion,Evento evento) {
		Sesion sesion = sesionRepository.findById(idSesion).orElseThrow(() -> new RuntimeException("Sesion no encontrada"));
		evento.setSesion(sesion);
		eventoRepository.save(evento);
	}

}
