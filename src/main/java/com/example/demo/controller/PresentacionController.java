package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	
	@PostMapping("/guardar")
	public void guardarPresentacion(@RequestBody Presentacion presentacion) {
		presentacionService.Guardar(presentacion);
		
	}

}
