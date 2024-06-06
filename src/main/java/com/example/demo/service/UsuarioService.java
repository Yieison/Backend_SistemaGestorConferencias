package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;


@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository  usuarioRepository;
	
	
	//traer todos los estudiantes
	public List<Usuario> getStudent(){
		return usuarioRepository.findAll();
	}
	
	//traer estudiante por id
		public Optional<Usuario> getUsuario(Integer id) {
			return usuarioRepository.findById(id);
		}
		
		//
		public void Guardar(Usuario usuario) {
			usuarioRepository.save(usuario);
		}
		
		public List<Usuario> getEvaluadores(Integer id){
			return usuarioRepository.findByRol(id);
		}
	

}
