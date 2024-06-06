package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Articulo;
import com.example.demo.model.Evaluacion;

import com.example.demo.model.Usuario;
import com.example.demo.repository.ArticuloRepository;
import com.example.demo.repository.EvaluacionRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.EvaluacionService;

@RestController
@RequestMapping("/evaluacion")
public class EvaluacionController {
	
	@Autowired
	EvaluacionService evaluacionService;
	
	
	
	@GetMapping
	public List<Evaluacion> getAll () {
		return evaluacionService.getEvaluacion();
	}
	
	
	@PostMapping("/save")
	public void save(@RequestBody Evaluacion evaluacion) {
		evaluacionService.Guardar(evaluacion);
	}
	
	
	
	@GetMapping("/{estado}")
	public Evaluacion getByid(@PathVariable String estado) {
		List<Evaluacion> evaluacion = evaluacionService.getEvaluacion(estado);
		return evaluacion.get(0);
	}
	
	
	@PutMapping("/{id}/realizarEvaluacion/{nuevoEstado}")
    public Evaluacion cambiarEstado(@PathVariable("id") int id, @PathVariable String nuevoEstado) {
        return evaluacionService.cambiarEstado(id, nuevoEstado);
    }
	
	
	
	
	

	
    


}
