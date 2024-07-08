package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Rol;
import com.example.demo.model.Usuario;
import com.example.demo.repository.RolRepository;
import com.example.demo.repository.UsuarioRepository;


@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository  usuarioRepository;
	
	@Autowired
	RolRepository rolRepository;
	
	
	
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
		
		public void RegistrarUsuario(Usuario usuario,int idRol) {
			Optional<Rol> rol = rolRepository.findById(idRol);
			usuario.setRol(rol.get());
			usuarioRepository.save(usuario);
		}
		
		public List<Usuario> getEvaluadores(String nombre){
			return usuarioRepository.findByRolNombre(nombre);
		}
		
		public Optional<Usuario> getCorreo(String correo){
			return usuarioRepository.findByCorreo(correo);
		}
		
		public void deleteUsuarios(Integer id) {
			usuarioRepository.deleteById(id);
		}
		
		
		public Usuario iniciarSesion(Usuario usu) {
			Optional<Usuario> us = usuarioRepository.findByCorreo(usu.getCorreo());
			if(us.isPresent() && us.get().getPassword().equals(usu.getPassword())) {
				return us.get();
			}
			return null;
		}
	

}
