package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Articulo;
import com.example.demo.model.Presentacion;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ArticuloRepository;
import com.example.demo.repository.PresentacionRepository;

@Service
public class PresentacionService {
	
	@Autowired
	PresentacionRepository presentacionRepository;
	
	
	@Autowired
	ArticuloRepository articuloRepository;
	
	public List<Presentacion> getPresentaciones(){
		return presentacionRepository.findAll();
	}
	
	//traer estudiante por id
		public Optional<Presentacion> getPresentacion(Integer id) {
			return presentacionRepository.findById(id);
		}
		
		//agregar presentacion de un articulo
		public void Guardar(Presentacion presentacion,int idArticulo) {
			Optional<Articulo> articuloOpt = articuloRepository.findById(idArticulo);
			if(articuloOpt.isPresent()) {
			Articulo articuloPresentar = articuloOpt.get();
			presentacion.setArticulo(articuloPresentar);
			presentacionRepository.save(presentacion);
			}
		}

}
