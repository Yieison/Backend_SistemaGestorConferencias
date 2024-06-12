package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
	
	@Query("SELECT u FROM Usuario u WHERE u.rol.nombre = ?1")
    List<Usuario> findByRolNombre(String nombreRol);
	
	
	Optional<Usuario> findByCorreo(String correo);

} 
