package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


import com.example.demo.model.Conferencia;
import com.example.demo.model.Topico;
import com.example.demo.model.Usuario;
import com.example.demo.service.AWSS3ServiceImpl;
import com.example.demo.service.ConferenciaService;
import com.example.demo.service.TopicoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/conferencias")
public class ConferenciaController {
	
	@Autowired
	ConferenciaService conferenciaService;
	
	@Autowired
	AWSS3ServiceImpl awss3ServiceImpl;
	
	
	@Autowired
	TopicoService topicoService;
	
	
	@GetMapping
	public List<Conferencia> getAll(){
		return conferenciaService.getAllConferencias();
	}
	
	@GetMapping("/{id}")
	public Conferencia getConferenciaById(@PathVariable int id) {
	  Optional<Conferencia> conferencia = conferenciaService.getConferencia(id);
	  return conferencia.get();
	}
	
	@GetMapping("/estado/{estado}")
	public List<Conferencia> getConferenciasByEstado(@PathVariable String estado){
		return conferenciaService.getConferenciasEstado(estado);
	}
	
	
	//guardar una conferencia
	@PostMapping("/saveConferencia/{idChair}")
	public void guardarConferencia(@RequestPart("file") MultipartFile file, 
		@RequestPart Conferencia conferencia,@PathVariable int idChair) {
		String fileUrl = awss3ServiceImpl.uploadFile(file);
		conferencia.setImagenUrl(fileUrl);
		conferenciaService.Guardar(conferencia,idChair);
	}
	
	 /**
	 @PostMapping("/save")
	    public ResponseEntity<String> guardarConferencia(@RequestPart("file") MultipartFile file, @RequestPart("conferencia") String conferenciaJson) {
	        try {
	            // Convertir el JSON de conferencia a objeto
	            ObjectMapper objectMapper = new ObjectMapper();
	            Conferencia conferencia = objectMapper.readValue(conferenciaJson, Conferencia.class);

	            // Subir archivo a AWS S3 y obtener la URL
	            String fileUrl = awss3ServiceImpl.uploadFile(file);

	            // Guardar la URL en la conferencia
	            conferencia.setImagenUrl(fileUrl);

	            // Guardar la conferencia en la base de datos
	            conferenciaService.Guardar(conferencia);

	            return new ResponseEntity<>("Conferencia guardada con éxito", HttpStatus.CREATED);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error al guardar la conferencia: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	 */
	
	
	//inactivar una conferencia
	@PostMapping("desactivar/conferencia/{id}")
	public ResponseEntity<?> desactivar(@PathVariable int id){
		try {
			conferenciaService.inactivarConferencia(id);
			return ResponseEntity.ok("Se cambio el estado de la conferencia exitosamente");
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cambiar el estado" + e.getMessage());
		}
	}
	 
	
	 
	 //Editar una conferencia
	 @PutMapping("editar/{id}/chair/{idChair}")
	    public ResponseEntity<?> actualizarConferencia(
	        @PathVariable Integer id,
	        @RequestPart(value="conferencia") Conferencia conferencia,@RequestPart(value = "file", required = false) MultipartFile archivoImagen,
	        @PathVariable int idChair
	    ) {
		 try {
		
		        conferenciaService.editarConferencia(conferencia, id, archivoImagen,idChair);
		        return ResponseEntity.ok("Conferencia actualizada");
		    } catch (RuntimeException e) {
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		    } catch (Exception e) {
		        // Manejar cualquier otro error genérico
		        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar la conferencia");
		    }
	    }
		
	 
	 
	 //obtener conferencias asociadas a un chair
	 @GetMapping("/lista/{idChair}")
	 public List<Conferencia> getConferenciasChair(@PathVariable Integer idChair){
		 return conferenciaService.getConferenciasChair(idChair);
	 }
	
	
	//obtener topicos de una conferencia
	@GetMapping("/{id}/topicos")
	public List<Topico> getTopicosById(@PathVariable Integer id) {
		Optional<Conferencia> conferencia = conferenciaService.getConferencia(id);
		
		List<Topico> topicos = topicoService.getTopicosConferencia(conferencia.get());
		
		return topicos;
		
	}
	
	
	
	//borra los topicos de una conferencia por Id
	/**
	@DeleteMapping("/{idConferencia}/topico/{idTopico}")
	public void deleteTopicosByIdConferencia(@PathVariable int idConferencia ,@PathVariable int idTopico) {
		
		//obtengo la conferencia
		Optional<Conferencia> conferencia = conferenciaService.getConferencia(idConferencia);
		//obtengo los topicos de la conferencia
		List<Topico> topicos = topicoService.getTopicosConferencia(conferencia.get());
		
		topicoService.delete(idTopico);
		
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

	*/
	
	//Asigna el chair a una conferencia
	@PostMapping("/asignarChair/{id}")
	public void asignarChair(@PathVariable int id,@RequestBody Conferencia conferencia) {
		conferenciaService.AsignarChair(id, conferencia);
	}
	
	@GetMapping("/conferenciasPasadas")
	public List<Conferencia> verConferenciasPasadas(LocalDate fecha){
		fecha= LocalDate.now();
		return conferenciaService.obtenerConferencisTerminadas(fecha);
	}
	
	
	 @PostMapping("/desactivarConferenciasPasadas")
	    public ResponseEntity<String> desactivarConferenciasPasadas() {
	        conferenciaService.desactivarConferenciasConFechaFinAntesDe(LocalDate.now());
	        return ResponseEntity.ok("Conferencias pasadas desactivadas exitosamente.");
	  }
	
}
