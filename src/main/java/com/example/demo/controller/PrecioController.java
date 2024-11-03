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

import com.example.demo.model.Precio;
import com.example.demo.service.PrecioService;

@RestController
@RequestMapping("/precios")
public class PrecioController {
	
	@Autowired
	PrecioService precioService;
	
	@GetMapping
	public List<Precio> getPrecios(){
		return precioService.getPrecios();
	}
	
	@PostMapping("/agregarPrecio/{idConferencia}")
	public ResponseEntity<String> agregarPrecios(@PathVariable int idConferencia,@RequestBody Precio precio){
		precioService.agregarPrecios(idConferencia, precio);
		return new ResponseEntity<>("el precio fue agregado exitosamente",HttpStatus.OK);
	}
	
	@PutMapping("/editarPrecio/{idPrecio}")
	public Precio editarPrecio(@PathVariable int idPrecio,@RequestBody Precio precio) {
		return precioService.editarPrecio(idPrecio, precio);
	}
	
	

}
