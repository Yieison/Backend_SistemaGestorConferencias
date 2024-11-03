package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

import jakarta.mail.internet.MimeMessage;

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
	
	
	/**
	@PostMapping("/save/articulo/{idArticulo}/evaluador/{idEvaluador}")
	public void saveEvaluacion(@PathVariable int idEvaluador , @PathVariable int idArticulo,@RequestBody Evaluacion evaluacion) {
		evaluacionService.asignarEvaluacion(idEvaluador, idArticulo, evaluacion);
	}
	*/
	
	 @PostMapping("/asignar/{idEvaluador}/evaluacion/{idArticulo}")
	    public ResponseEntity<String> saveEvaluacion(
	            @PathVariable int idEvaluador,
	            @PathVariable int idArticulo,
	            @RequestBody Evaluacion evaluacion) {
		 	
	        evaluacionService.asignarEvaluacion(idEvaluador, idArticulo, evaluacion);
	        return new ResponseEntity<>("Evaluación y correo asignado exitosamente", HttpStatus.OK);
	   }
	
	
	
	
	@GetMapping("/{estado}")
	public List<Evaluacion> getByEstado(@PathVariable String estado) {
	    List<Evaluacion> evaluaciones = evaluacionService.getEvaluacion(estado);
	    return evaluaciones;
	}
	
	
	@PutMapping("/{id}/realizarEvaluacion/{nuevoEstado}")
    public ResponseEntity<String >cambiarEstado(@PathVariable("id") int id, @PathVariable String nuevoEstado,@RequestBody String comentario) {
       evaluacionService.cambiarEstado(id, nuevoEstado,comentario);
       return ResponseEntity.ok("la evaluacion ha sido exitosa");
    }
	
	
	@GetMapping("/articulo/{idArticulo}")
    public List<Evaluacion> obtenerEvaluacionesPorArticulo(@PathVariable int idArticulo) {
        return evaluacionService.obtenerEvaluacionesByArticulo(idArticulo);
    }
	
	
	@GetMapping("/conferencia/{idConferencia}")
	public List<Evaluacion> getEvaluacionesByConferencia(@PathVariable int idConferencia){
		return evaluacionService.getEvaluacionesConferencia(idConferencia);
	}
	
	
	
	
	

	
    


}
