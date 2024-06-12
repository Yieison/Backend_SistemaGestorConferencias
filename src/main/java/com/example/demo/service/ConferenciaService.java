package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Conferencia;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ConferenciaRepository;

@Service
public class ConferenciaService {
	
	@Autowired
	ConferenciaRepository conferenciaRepository;
	
	public List<Conferencia> getAllConferencias(){
		return conferenciaRepository.findAll();
	}
	
	//traer conferencia por id
		public Optional<Conferencia> getConferencia(Integer id) {
			return conferenciaRepository.findById(id);
		}
		
		//
		public void Guardar(Conferencia conferencia) {
			conferenciaRepository.save(conferencia);
		}

}
