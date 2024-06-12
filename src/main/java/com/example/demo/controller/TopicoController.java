package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Conferencia;
import com.example.demo.model.Topico;
import com.example.demo.service.TopicoService;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
	
	@Autowired
	TopicoService topicoService;
	
	@GetMapping
	public List<Topico> getAll(){
		return topicoService.getAllTopics();
	}
	
	@PostMapping("/save")
	public void guardarTopico(@RequestBody Topico topico) {
		topicoService.Guardar(topico);
	}
	
	@DeleteMapping("/{id}")
	public void eliminarTopico(@PathVariable Integer id) {
		topicoService.delete(id);
	}
	

}
