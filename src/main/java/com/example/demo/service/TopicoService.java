package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Ciudad;
import com.example.demo.model.Conferencia;
import com.example.demo.model.Topico;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ConferenciaRepository;
import com.example.demo.repository.TopicoRepository;

@Service
public class TopicoService {
	
	@Autowired
	TopicoRepository topicoRepository;
	
	@Autowired
	ConferenciaRepository conferenciaRepository;
	
	
	
	public List<Topico> getAllTopics (){
		return topicoRepository.findAll();
	}
	
	public Optional<Topico> getTopicoById(Integer id) {
		return topicoRepository.findById(id);
	}
	
	public void Guardar(Topico topico,int idConferencia) {
		Conferencia conferencia = conferenciaRepository.findById(idConferencia).orElseThrow(()-> 
		new RuntimeException("Conferencia no encontrada"));
		topico.setConferencia(conferencia);
		topicoRepository.save(topico);
	}
	
	
	public List<Topico> getTopicosConferencia(Conferencia conferencia) {
        // Utiliza el repositorio para obtener los tópicos asociados a la conferencia
        return topicoRepository.findByConferencia(conferencia);
    }
	
	public void delete(Integer id) {
		topicoRepository.deleteById(id);
	}
	
	public Topico editarTopico(int idTopico, Topico topicoActualizado) {
		Topico topico = topicoRepository.findById(idTopico)
                .orElseThrow(() -> new RuntimeException("Topico no encontrado"));
		topico.setTema(topicoActualizado.getTema());
		return topicoRepository.save(topico);
	}
	
}
