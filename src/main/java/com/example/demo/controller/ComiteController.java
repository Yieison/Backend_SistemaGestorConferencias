package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Comite;
import com.example.demo.model.Usuario;
import com.example.demo.service.ComiteService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;


@RestController
@RequestMapping("/comites")
public class ComiteController {
	
	
	@Autowired
	ComiteService comiteService;
	
	
	@GetMapping
	public ResponseEntity<List<Comite>> obtenerComites(){
		return ResponseEntity.ok(comiteService.getAllComites());
	}
	
	
	 @PostMapping("/agregarComite/conferencia/{idConferencia}")
	    public ResponseEntity<String> agregarComite(@PathVariable int idConferencia, @RequestBody Comite comite) {
	        comiteService.agregarComite(idConferencia, comite);
	        return ResponseEntity.ok("Comité agregado exitosamente");
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
