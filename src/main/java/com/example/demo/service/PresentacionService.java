package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Articulo;
import com.example.demo.model.Presentacion;
import com.example.demo.model.Sala;
import com.example.demo.model.Sesion;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ArticuloRepository;
import com.example.demo.repository.PresentacionRepository;
import com.example.demo.repository.SesionRepository;

@Service
public class PresentacionService {
	
	@Autowired
	PresentacionRepository presentacionRepository;
	
	@Autowired
	SesionRepository sesionRepository;
	
	@Autowired
	ArticuloRepository articuloRepository;
	
	public List<Presentacion> getPresentaciones(){
		return presentacionRepository.findAll();
	}
	
	public List<Presentacion> getPresentacionesSesion(int idSesion){
      return presentacionRepository.findByPresentaciones(idSesion);
	}
	
	//traer estudiante por id
		public Optional<Presentacion> getPresentacion(Integer id) {
			return presentacionRepository.findById(id);
		}
		
		//agregar presentacion de un articulo
		public void Guardar(Presentacion presentacion,int idArticulo,int idSesion) {
			Optional<Articulo> articuloOpt = articuloRepository.findById(idArticulo);
			Sesion sesion = sesionRepository.findById(idSesion)
					.orElseThrow(() -> new RuntimeException("sesion no encontrada"));
			if(articuloOpt.isPresent()) {
			Articulo articuloPresentar = articuloOpt.get();
			presentacion.setArticulo(articuloPresentar);
			presentacion.setSesion(sesion);
			presentacionRepository.save(presentacion);
			}
		}

}
