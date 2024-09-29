package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Presentacion;
import com.example.demo.service.PresentacionService;

@RestController
@RequestMapping("/presentaciones")
public class PresentacionController {
	
	@Autowired
	PresentacionService presentacionService;
	
	@GetMapping
	public List<Presentacion> getAll(){
		return presentacionService.getPresentaciones();
	}
	
	@GetMapping("/sesion/{idSesion}")
	public List<Presentacion> getPresentacionesSesion(@PathVariable int idSesion){
		return presentacionService.getPresentacionesSesion(idSesion);
	}
	
	
	@PostMapping("/guardar/{id}/sesion/{idSesion}")
	public void guardarPresentacion(@RequestBody Presentacion presentacion,@PathVariable int id,@PathVariable int idSesion) {
		presentacionService.Guardar(presentacion, id,idSesion);
	}

}
