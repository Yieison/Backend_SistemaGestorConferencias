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

import com.example.demo.model.Convocatoria;
import com.example.demo.service.ConvocatoriaService;

@RestController
@RequestMapping("convocatorias")
public class ConvocatoriaController {
	
	@Autowired
	ConvocatoriaService convocatoriaService;
	
	@GetMapping
	public List<Convocatoria> getAllConvocatories(){
		return convocatoriaService.getConvocatorias();
	}
	
	@PostMapping("/agregar/{idConferencia}")
	public ResponseEntity<String> agregarConvocatoria(@RequestBody Convocatoria convocatoria,@PathVariable int idConferencia){
		convocatoriaService.agregarConvocatoria(idConferencia, convocatoria);
		return new ResponseEntity<>("convocatoria agregada exitosamente", HttpStatus.OK);
	}

}
