package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.Conferencia;
import com.example.demo.model.Precio;
import com.example.demo.repository.ConferenciaRepository;
import com.example.demo.repository.PrecioRepository;

@Service
public class PrecioService {
	
	@Autowired
	PrecioRepository precioRepository;
	
	@Autowired
	ConferenciaRepository conferenciaRepository;
	
	
	public List<Precio> getPrecios(){
		return precioRepository.findAll();
	}
	
	public void agregarPrecios(int idConferencia,Precio precio){
		Conferencia conferencia = conferenciaRepository.findById(idConferencia).orElseThrow(() -> new RuntimeException("Conferencia no encontrada"));
		precio.setConferencia(conferencia);
		precioRepository.save(precio);	
	}
}
