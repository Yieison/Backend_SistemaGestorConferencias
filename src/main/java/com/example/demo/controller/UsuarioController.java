package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.UsuarioService;



@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	

	@Autowired
	UsuarioService usuarioService;
	
	
	
	//listar todos
	@GetMapping
	public List<Usuario> getAll(){
		return usuarioService.getStudent();
	}
	
	@GetMapping("/{id}")
	public Usuario getByid(@PathVariable Integer id) {
		Optional<Usuario> usuario = usuarioService.getUsuario(id);
		return usuario.get();
	}
	
	@PostMapping("/save")
	public void save(@RequestBody Usuario usuario) {
		usuarioService.Guardar(usuario);
	}
	
	
	 @GetMapping("/findUsuarios/{rol}")
	    public List<Usuario> getUsuariosPorRol(@PathVariable("rol") String nombre) {
	        return usuarioService.getEvaluadores(nombre);
	 }
	 
	 @GetMapping("/findCorreo/{correo}")
	    public Optional<Usuario> getUsuariosPorCorreo(@PathVariable("correo") String correo) {
	        return usuarioService.getCorreo(correo);
	 }
	 
	 
	 @PostMapping("/iniciarSesion")
	 public ResponseEntity<Usuario> iniciarSesion (@RequestBody Usuario login){
		 return new ResponseEntity<>(usuarioService.iniciarSesion(login),HttpStatus.OK);
		 
	 }
	

}
