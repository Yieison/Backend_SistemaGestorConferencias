package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Conferencia;
import com.example.demo.model.Topico;
import com.example.demo.model.Usuario;
import com.example.demo.repository.TopicoRepository;

@Service
public class TopicoService {
	
	@Autowired
	TopicoRepository topicoRepository;
	
	
	
	public List<Topico> getAllTopics (){
		return topicoRepository.findAll();
	}
	
	public Optional<Topico> getTopicoById(Integer id) {
		return topicoRepository.findById(id);
	}
	
	public void Guardar(Topico topico) {
		topicoRepository.save(topico);
	}
	
	
	public List<Topico> getTopicosConferencia(Conferencia conferencia) {
        // Utiliza el repositorio para obtener los tópicos asociados a la conferencia
        return topicoRepository.findByConferencia(conferencia);
    }
	
	public void delete(Integer id) {
		topicoRepository.deleteById(id);
	}
	
}
