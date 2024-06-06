package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Presentacion;
import com.example.demo.model.Usuario;
import com.example.demo.repository.PresentacionRepository;

@Service
public class PresentacionService {
	
	@Autowired
	PresentacionRepository presentacionRepository;
	
	public List<Presentacion> getPresentaciones(){
		return presentacionRepository.findAll();
	}
	
	//traer estudiante por id
		public Optional<Presentacion> getPresentacion(Integer id) {
			return presentacionRepository.findById(id);
		}
		
		//
		public void Guardar(Presentacion presentacion) {
			presentacionRepository.save(presentacion);
		}

}
