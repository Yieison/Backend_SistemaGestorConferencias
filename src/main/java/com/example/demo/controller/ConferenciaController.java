package com.example.demo.controller;

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
	
	
	//guardar una conferencia
	@PostMapping("/saveConferencia")
	public void guardarConferencia(@RequestPart("file") MultipartFile file, @RequestPart Conferencia conferencia) {
		String fileUrl = awss3ServiceImpl.uploadFile(file);
		conferencia.setImagenUrl(fileUrl);
		conferenciaService.Guardar(conferencia);
	}
	
	
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
	 
	 //Eliminar una confencia
	 @DeleteMapping("eliminar/{id}")
		public ResponseEntity<String> eliminar(@PathVariable Integer id) {
		 	Optional<Conferencia> conferenciaOpt = conferenciaService.getConferencia(id);
		 	if(conferenciaOpt.isPresent()&& conferenciaOpt.get().getChair()== null) {
		 		Conferencia conferencia = conferenciaOpt.get();
		 		String imagenUrl = conferencia.getImagenUrl();
		 		//extraer la clave del objeto (key) de la url completa
		 		String objectKey = imagenUrl.substring(imagenUrl.lastIndexOf("/")+1);
		 		
		 		try {
		 			awss3ServiceImpl.deleteObjectFromS3(objectKey);
		 			conferenciaService.delete(id);
		 			return ResponseEntity.ok("conferencia eliminada exitosamente");
		 		}catch (Exception e) {
		 			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la conferencia:" + e.getMessage());
		 		}
		 	}else {
		 		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("la conferencia tiene un chair registrado");
		 		
		 	}
		 	
		}
	 
	 //Editar una conferencia
	 @PutMapping("editar/{id}")
	    public ResponseEntity<?> actualizarConferencia(
	        @PathVariable Integer id,
	        @RequestPart(required = false ) Conferencia conferencia,@RequestPart(value = "file", required = false) MultipartFile archivoImagen
	    ) {
	        Optional<Conferencia> conferenciaOpt = conferenciaService.getConferencia(id);

	        if (conferenciaOpt.isPresent()) {
	        	
	            Conferencia conferenciaAct  = conferenciaOpt.get();
	            
	            
	            if (archivoImagen != null && !archivoImagen.isEmpty()) {
	                // Eliminar la imagen antigua del bucket S3 si existe
	                String imagenUrlAntigua = conferenciaAct.getImagenUrl();
	                if (imagenUrlAntigua != null && !imagenUrlAntigua.isEmpty()) {
	                    String objectKeyAntiguo = imagenUrlAntigua.substring(imagenUrlAntigua.lastIndexOf("/") + 1);
	                    awss3ServiceImpl.deleteObjectFromS3(objectKeyAntiguo);
	                    System.out.println("Imagen antigua eliminada: " + objectKeyAntiguo); // Log para depuración
	                }

	                // Subir la nueva imagen al bucket S3 y obtener su URL
	                String nuevaImagenUrl = awss3ServiceImpl.uploadFile(archivoImagen);
	                conferenciaAct.setImagenUrl(nuevaImagenUrl);
	                System.out.println("Nueva imagen subida: " + nuevaImagenUrl); // Log para depuración
	            }
	            
	            
	            conferenciaService.editarConferencia(conferenciaAct);
	            
	            return ResponseEntity.ok("Conferencia actualizada");
	        } else {
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conferencia no encontrada");
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
	@DeleteMapping("/{idConferencia}/topico/{idTopico}")
	public void deleteTopicosByIdConferencia(@PathVariable int idConferencia ,@PathVariable int idTopico) {
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
	
	
	//Asigna el chair a una conferencia
	@PostMapping("/asignarChair/{id}")
	public void asignarChair(@PathVariable int id,@RequestBody Conferencia conferencia) {
		conferenciaService.AsignarChair(id, conferencia);
	}
	
}
