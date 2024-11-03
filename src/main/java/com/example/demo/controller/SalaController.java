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

import com.example.demo.model.Sala;
import com.example.demo.service.SalaService;

@RestController
@RequestMapping("/salas")
public class SalaController {
	
	@Autowired
	SalaService salaService;
	
	@GetMapping
	public List<Sala> getSalas(){
		return salaService.getSalas();
	}
	
	@PostMapping("/agregarSala/{idInstitucion}")
	public ResponseEntity<String> agregarSala(@RequestBody Sala sala,@PathVariable int idInstitucion){
		salaService.agregarSalas(sala,idInstitucion);
		return new ResponseEntity<>("sala agregada exitosamente", HttpStatus.OK);
	}
	
	
	@PutMapping("/editarSala/{idSala}")
	public ResponseEntity<String> editarSala(@PathVariable int idSala , @RequestBody Sala sala ){
		salaService.editarSala(idSala, sala);
		return new ResponseEntity<>("sala editada exitosamente", HttpStatus.OK);
    }


}
