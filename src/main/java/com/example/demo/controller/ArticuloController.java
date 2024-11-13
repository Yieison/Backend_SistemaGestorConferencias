package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Articulo;
import com.example.demo.model.Usuario;
import com.example.demo.service.AWSS3ServiceImpl;
import com.example.demo.service.ArticuloService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/articulos")
public class ArticuloController {
	
	@Autowired
	ArticuloService articuloService;
	
	@Autowired
	AWSS3ServiceImpl awss3ServiceImpl;
	
	//listar todos
		@GetMapping
		public List<Articulo> getAll(){
			return articuloService.getArticulo();
		}
		
		
		//listar por estado
		 @GetMapping("/estado/{estado}")
		    public List<Articulo> getArticulosPorEstado(@PathVariable("estado") String estado) {
		        return articuloService.getArticuloEstado(estado);
		 }
		 
		 @GetMapping("/conferencia/{idConferencia}")
		 public List<Articulo> getArticulosConferencia(@PathVariable int idConferencia){
			 return articuloService.getArticuloConferencia(idConferencia);
		 }
		 
		 @PostMapping("/save/{idConferencia}/autor/{idAutor}")
		 public ResponseEntity<String> saveArticulo(@RequestPart("file") MultipartFile file,
				 @PathVariable int idConferencia,
				 @PathVariable int idAutor,
				 @RequestPart("articulo")  Articulo articulo){
			  String folder = String.valueOf(idConferencia); // Esto sería "articulos/{idConferencia}"

			    // Subir el archivo a la carpeta de conferencia y obtener la URL
			    String fileUrl = awss3ServiceImpl.uploadFileCarpeta(file, folder);
			 articulo.setUrl(fileUrl);
			 articuloService.saveArticulo(idConferencia, idAutor, articulo);
			 return new ResponseEntity <> ("Articulo guardado exitosamente",HttpStatus.OK);
		 }
		 
		 @GetMapping("/autor/{idAutor}")
		 public List<Articulo> getArticulosAutor(@PathVariable int idAutor){
			 return articuloService.getArticulosAutor(idAutor);
		 }
		 
		 
		 @PutMapping("/estadoFinal/{idArticulo}")
		 public ResponseEntity<String> decidirEstadofinalArticulo(@PathVariable int idArticulo,@RequestParam String estadoFinal){
			 articuloService.decidirEstadoFinalArticulo(idArticulo, estadoFinal);
			 return ResponseEntity.ok("La evaluacion del articulo fue correctamente enviada al autor");
		 }
		 
		 

}
