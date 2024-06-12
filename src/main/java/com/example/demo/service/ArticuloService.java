package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Articulo;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ArticuloRepository;

@Service
public class ArticuloService {
	
	@Autowired
	ArticuloRepository articuloRepository;
	
	public List<Articulo> getArticulo(){
		return articuloRepository.findAll();
	}
	
	//traer todos los articulos por estado
	public List<Articulo> getArticuloEstado(String estado){
		return articuloRepository.findByEstado(estado);
	}
	
	

}
