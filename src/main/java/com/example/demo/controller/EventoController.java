package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Evento;
import com.example.demo.service.EventoService;

@RestController
@RequestMapping("/eventos")
public class EventoController {
	
	@Autowired
	EventoService eventoService;
	
	@GetMapping
	public List<Evento> getEventos(){
		return eventoService.getEventos();
	}
	
	@GetMapping("/sesion/{idSesion}")
	public List<Evento> getEventosSesion(@PathVariable int idSesion){
		return eventoService.getEventosSesion(idSesion);
	}
	
	@PostMapping("/agregarEvento/{idSesion}")
	public ResponseEntity<String> agregarEvento(@PathVariable int idSesion,@RequestBody Evento evento){
		eventoService.agregarEvento(idSesion, evento);
		return new ResponseEntity<>("evento agregado exitosamente",HttpStatus.OK);
	}
	
	

}
