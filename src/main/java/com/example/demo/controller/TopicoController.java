package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@PostMapping("/save/{idConferencia}")
	public void guardarTopico(@RequestBody Topico topico,@PathVariable int idConferencia) {
		topicoService.Guardar(topico, idConferencia);
	}
	
	@PutMapping("/editar/{idTopico}")
	public Topico editarTopico(@PathVariable int idtopico, @RequestBody Topico topico) {
		return topicoService.editarTopico(idtopico, topico);
	}
	
	@DeleteMapping("/{id}")
	public void eliminarTopico(@PathVariable Integer id) {
		topicoService.delete(id);
	}
	

}
