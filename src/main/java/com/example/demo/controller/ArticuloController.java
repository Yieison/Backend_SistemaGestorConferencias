package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Articulo;
import com.example.demo.service.ArticuloService;

@RestController
@RequestMapping("/articulos")
public class ArticuloController {
	
	@Autowired
	ArticuloService articuloService;
	
	//listar todos
		@GetMapping
		public List<Articulo> getAll(){
			return articuloService.getArticulo();
		}

}
