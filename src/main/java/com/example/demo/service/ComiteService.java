package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Comite;
import com.example.demo.model.Conferencia;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ComiteRepository;
import com.example.demo.repository.ConferenciaRepository;
import com.example.demo.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ComiteService {
	
	
	
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	
	@Autowired
	ComiteRepository comiteRepository;
	
	@Autowired
	ConferenciaRepository conferenciaRepository;
	
	
	public List<Comite> getAllComites(){
		return comiteRepository.findAll();
	}
	
	public void agregarComite(int idConferencia, Comite comite) {
	
	
		 if (comite == null) {
		        throw new IllegalArgumentException("El comité no puede ser null.");
		    }

		    Optional<Conferencia> optConferencia = conferenciaRepository.findById(idConferencia);
		    if (optConferencia.isPresent()) {
		        Conferencia conferencia = optConferencia.get();

		        // Verifica que el nombre del comité no sea null o vacío
		        if (comite.getNombre() != null && !comite.getNombre().trim().isEmpty()) {
		            // Asigna la conferencia al comité
		            comite.setConferencia(conferencia);

		            // Guarda el comité en el repositorio
		            comiteRepository.save(comite);
		        } else {
		            // Maneja el caso donde el nombre del comité sea null o vacío
		            throw new IllegalArgumentException("El nombre del comité no puede ser null o vacío.");
		        }
		    } else {
		        // Maneja el caso en que la conferencia no exista
		        throw new EntityNotFoundException("Conferencia con ID " + idConferencia + " no encontrada.");
		    }    
	}
	
	
	public Comite agregarMiembrosComite(int idUsuario,int idComite){
		
		Comite comite = comiteRepository.findById(idComite)
                .orElseThrow(() -> new RuntimeException("Comité no encontrado"));


        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        comite.getUsuarios().add(usuario);
		
		comiteRepository.save(comite);
		
		return comite;
	}
	
	
	@Transactional
	public Comite agregarMiembrosComiteNuevos (int idComite,Usuario usuario){
		
		Comite comite = comiteRepository.findById(idComite)
        .orElseThrow(() -> new RuntimeException("Comité no encontrado"));
		
	    // Guardar el nuevo usuario si no existe
	     Usuario usuarioGuardado = usuarioRepository.save(usuario);
	     
	     Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioGuardado.getId_usuarios());
	     
        comite.getUsuarios().add(usuarioOpt.get());
		    
	  
	    // Guardar el comité con el nuevo miembro
	    comiteRepository.save(comite);
	    
	    return comite;
	}
	
	
	

}
