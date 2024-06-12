package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

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
import com.example.demo.model.Usuario;
import com.example.demo.service.ConferenciaService;
import com.example.demo.service.TopicoService;

@RestController
@RequestMapping("/conferencias")
public class ConferenciaController {
	
	@Autowired
	ConferenciaService conferenciaService;
	
	
	@Autowired
	TopicoService topicoService;
	
	
	@GetMapping
	public List<Conferencia> getAll(){
		return conferenciaService.getAllConferencias();
	}
	
	@PostMapping("/save")
	public void guardarConferencia(@RequestBody Conferencia conferencia) {
		conferenciaService.Guardar(conferencia);
	}
	
	
	@GetMapping("/{id}/topicos")
	public List<Topico> getTopicosById(@PathVariable Integer id) {
		Optional<Conferencia> conferencia = conferenciaService.getConferencia(id);
		
		List<Topico> topicos = topicoService.getTopicosConferencia(conferencia.get());
		
		return topicos;
		
	}
	
	@DeleteMapping("/{idConferencia}/topico/{idTopico}")
	public void getTopicosById(@PathVariable int idConferencia ,@PathVariable int idTopico) {
		/**
		//obtengo la conferencia
		Optional<Conferencia> conferencia = conferenciaService.getConferencia(idConferencia);
		//obtengo los topicos de la conferencia
		List<Topico> topicos = topicoService.getTopicosConferencia(conferencia.get());
		
		topicoService.delete(idTopico);
		*/
		Optional<Conferencia> optionalConferencia = conferenciaService.getConferencia(idConferencia);
	    
	    // Verificar si la conferencia existe
	    if (optionalConferencia.isPresent()) {
	        // Obtener el tópico por su ID
	        Optional<Topico> optionalTopico = topicoService.getTopicoById(idTopico);
	        
	        // Verificar si el tópico existe
	        if (optionalTopico.get().getConferencia().getId_conferencia() == idConferencia) {
	            // Eliminar el tópico
	            topicoService.delete(idTopico);
	        } else {
	            throw new IllegalArgumentException("El tópico especificado no pertenece a la conferencia especificada.");
	        	}
	
	    		}
	    
	}
	
}
