package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Ciudad;
import com.example.demo.model.Institucion;
import com.example.demo.model.Pais;
import com.example.demo.service.UbicacionService;

@RestController
@RequestMapping("/ubicacion")
public class UbicacionController {
	
	@Autowired
	UbicacionService ubicacionService;
	
	
	@GetMapping("/pais")
	public List<Pais> getPaises(){
		return ubicacionService.getAllPaises();
	}
	
	@GetMapping("/ciudad")
	public List<Ciudad> getCiudades(){
		return ubicacionService.getAllCiudades();
	}
	
	@GetMapping("/ciudades/{idPais}")
	public List<Ciudad> getCiudadesByPais(@PathVariable int idPais){
		return ubicacionService.getCiudadesByPais(idPais);
	}
	
	@GetMapping("/institucion")
	public List<Institucion> getInstituciones(){
		return ubicacionService.getAllInstituciones();
	}
	
	 @GetMapping("instituciones/{idCiudad}")
	 public List<Institucion> getInstitucionesByCiudad(@PathVariable int idCiudad) {
	 return ubicacionService.getInstitucionesByCiudad(idCiudad);
	 }
	
	
	

}
