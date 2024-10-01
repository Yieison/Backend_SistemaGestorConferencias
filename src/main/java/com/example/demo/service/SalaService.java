package com.example.demo.service;

import java.util.List;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Institucion;
import com.example.demo.model.Sala;
import com.example.demo.repository.InstitucionRepository;
import com.example.demo.repository.SalaRepository;

@Service
public class SalaService {
	
	@Autowired
	SalaRepository salaRepository;
	
	@Autowired
	InstitucionRepository institucionRepository;
	
	public List<Sala> getSalas(){
		return salaRepository.findAll();
	}
	
	public void agregarSalas(Sala sala,int idInstitucion) {
		Institucion institucion = institucionRepository.findById(idInstitucion).orElseThrow(()-> new RuntimeException("institucion no encontrada"));
		sala.setInstitucion(institucion);
		salaRepository.save(sala);
	}
	
	public Sala editarSala(int idSala,Sala salaedit) {
		Sala salaExistente = salaRepository.findById(idSala)
		.orElseThrow(() -> new RuntimeException("sala no encontrada"));
		salaExistente.setNombre(salaedit.getNombre());
		salaExistente.setTipo(salaedit.getTipo());
		return salaRepository.save(salaExistente);
	}

}
