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

import com.example.demo.model.Comite;

import com.example.demo.model.Usuario;
import com.example.demo.service.ComiteService;
import com.fasterxml.jackson.databind.ObjectMapper;


import jakarta.persistence.EntityNotFoundException;


@RestController
@RequestMapping("/comites")
public class ComiteController {
	
	
	@Autowired
	ComiteService comiteService;
	
	 private final ObjectMapper objectMapper; // Declarar ObjectMapper

	    public ComiteController(ComiteService comiteService, ObjectMapper objectMapper) {
	        this.comiteService = comiteService;
	        this.objectMapper = objectMapper; // Inicializar ObjectMapper
	    }
	
	
	@GetMapping
	public ResponseEntity<List<Comite>> obtenerComites(){
		return ResponseEntity.ok(comiteService.getAllComites());
	}
	
	
	@PostMapping("/agregarComite/conferencia/{idConferencia}")
	public ResponseEntity<String> agregarComite(@PathVariable int idConferencia, @RequestBody Comite comite) {
		
	    System.out.println("Comité recibido: " + comite.getNombre()); // Muestra el objeto recibido

	    // Comprueba si el nombre del comité no es null antes de crear el objeto Comite
	    if (comite.getNombre() != null && !comite.getNombre().trim().isEmpty()) {
	        comiteService.agregarComite(idConferencia, comite);
	        return ResponseEntity.ok("Comité agregado exitosamente");
	    } else {
	        return ResponseEntity.badRequest().body("El nombre del comité no puede ser null o vacío.");
	    }
	}
	
	@PostMapping("/testComite")
    public ResponseEntity<String> testComite(@RequestBody String json) {
        try {
            // Deserializa manualmente el JSON a la clase Comite
            Comite comite = objectMapper.readValue(json, Comite.class);
            System.out.println("Comité recibido: " + comite);
            return ResponseEntity.ok("Comité recibido: " + comite.getNombre());
        } catch (Exception e) {
            // Manejo de excepciones en caso de error de deserialización
            e.printStackTrace();
            return ResponseEntity.status(400).body("Error en la deserialización: " + e.getMessage());
        }
    }
	
	
	@PostMapping("/agregarMiembros/comite/{idComite}/usuario/{idUsuario}")
	public ResponseEntity<Comite> agregarUsuario(@PathVariable int idComite,@PathVariable int idUsuario){
		Comite comiteActualizado = comiteService.agregarMiembrosComite(idUsuario, idComite);
		return ResponseEntity.ok(comiteActualizado);
	}
	
	

	@PostMapping("/agregarMiembros/comite/{idComite}")
	public ResponseEntity<?> agregarUsuarioNuevo(@PathVariable int idComite,@RequestBody Usuario usuario){
		try {
	        Comite comite = comiteService.agregarMiembrosComiteNuevos(idComite, usuario);
	        return ResponseEntity.ok(comite);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	    }
	}


}
