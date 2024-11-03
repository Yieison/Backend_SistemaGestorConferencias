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

import com.example.demo.model.Sesion;
import com.example.demo.service.SesionService;


@RestController
@RequestMapping("/sesiones")
public class SesionController {
	
	@Autowired
	SesionService sesionService;
	
	@GetMapping
	public List<Sesion> getAllSesiones() {
		return sesionService.getAllSesiones();
	}
	
	
	@GetMapping("/conferencia/{idConferencia}")
	public List<Sesion> getSesionesConferencia(@PathVariable int idConferencia){
		return sesionService.getSesionesConferencia(idConferencia);
	}
	
	@PostMapping("/agregar/{idConferencia}/sala/{idSala}")
	public ResponseEntity<String> agregarSesion(@PathVariable int idConferencia,@RequestBody Sesion sesion,
			@PathVariable int idSala){
		sesionService.agregarSesion(idConferencia, sesion, idSala);
		return new ResponseEntity<>("sesion agregada exitosamente",HttpStatus.OK);
	}
	
	
	@GetMapping("/ordenadas")
    public List<Sesion> listarSesionesOrdenadas() {
        return sesionService.listarSesionesOrdenadasPorHora();
    }
	
	@PutMapping("/editarSesiones/{idSesion}")
	public Sesion editarSesiones(@PathVariable int idSesion,@RequestBody Sesion sesion){
		return sesionService.editarSesion(idSesion, sesion);
	}
	

}
