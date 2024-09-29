package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.Inscripcion;
import com.example.demo.model.Pago;
import com.example.demo.repository.InscripcionRepository;
import com.example.demo.repository.PagoRepository;

@Service
public class PagoService {
	
	@Autowired
	PagoRepository pagoRepository;
	
	@Autowired
	InscripcionRepository inscripcionRepository;
	
	public List<Pago> getPagos(){
		return pagoRepository.findAll();
	}
	
	public List<Pago> getPagosEstado(String estado){
		return pagoRepository.findPagoByEstado(estado);
	}
	
	public ResponseEntity<String> realizarPago(Pago pago,int idInscripcion){
		Inscripcion inscripcion = inscripcionRepository.findById(idInscripcion)
		.orElseThrow(() -> new RuntimeException("inscripcion no encontrada"));
		pago.setInscripcion(inscripcion);
		pagoRepository.save(pago);
		return new ResponseEntity<>("pago realizado exitosamente",HttpStatus.OK);
		
	}
	
	

}
