package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Articulo;
import com.example.demo.model.Conferencia;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ArticuloRepository;
import com.example.demo.repository.ConferenciaRepository;
import com.example.demo.repository.UsuarioRepository;

@Service
public class ArticuloService {
	
	@Autowired
	ArticuloRepository articuloRepository;
	
	@Autowired
	ConferenciaRepository conferenciaRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public List<Articulo> getArticulo(){
		return articuloRepository.findAll();
	}
	
	//traer todos los articulos por estado
	public List<Articulo> getArticuloEstado(String estado){
		return articuloRepository.findByEstado(estado);
	}
	
	public List<Articulo> getArticulosAutor(int idAutor){
		return articuloRepository.findArticulosByAutorId(idAutor);
	}
	
	
	public void saveArticulo(int idConferencia,int idAutor,Articulo articulo) {
		Optional<Conferencia> opConferencia = conferenciaRepository.findById(idConferencia);
		Optional<Usuario> opAutor = usuarioRepository.findById(idAutor);
		if(opConferencia.isPresent() && opAutor.isPresent()) {
			Conferencia conferencia = opConferencia.get();
			Usuario autor = opAutor.get();
			articulo.setConferencia(conferencia);
			articulo.setAutor(autor);
			articuloRepository.save(articulo);
		}
		
	}
	

}
