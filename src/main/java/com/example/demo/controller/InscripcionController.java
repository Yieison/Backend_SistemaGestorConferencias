package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Inscripcion;
import com.example.demo.service.InscripcionService;

@RestController
@RequestMapping("inscripciones")
public class InscripcionController {
	
	@Autowired
	InscripcionService inscripcionService;
	
	@GetMapping
	public List<Inscripcion> getAll(){
		return inscripcionService.getAllInscripciones();
	}
	
	@GetMapping("/estado/{estado}")
	public List<Inscripcion> getInscripcionesEstado(@PathVariable String estado){
		return inscripcionService.getInscripcionesEstado(estado);
	}
	
	@PostMapping("/registrar/{idUsuario}/conferencia/{idConferencia}")
	public ResponseEntity<String> saveInscripcion(
            @PathVariable int idUsuario,
            @PathVariable int idConferencia,
            @RequestBody Inscripcion inscripcion) {
        inscripcionService.registrarInscripcion(idUsuario, idConferencia, inscripcion);
        return new ResponseEntity<>("Evaluación asignada exitosamente", HttpStatus.OK);
   }
	
	@PutMapping("/activar/{idInscripcion}/new/{estado}")
	public Inscripcion Inscripcion(@PathVariable int idInscripcion,@PathVariable String estado) {
		return inscripcionService.cambiarEstadoInscripcion(idInscripcion, estado);
	}
	
	
	

}
