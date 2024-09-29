package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Conferencia;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ConferenciaRepository;
import com.example.demo.repository.UsuarioRepository;

@Service
public class ConferenciaService {
	
	@Autowired
	ConferenciaRepository conferenciaRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public List<Conferencia> getAllConferencias(){
		return conferenciaRepository.findAll();
	}
	
	//traer conferencia por id
		public Optional<Conferencia> getConferencia(Integer id) {
			return conferenciaRepository.findById(id);
		}
		
		//Guardar una conferencia
		public void Guardar(Conferencia conferencia,int id) {
			 List<Usuario> chairs = usuarioRepository.findByRolNombre("CHAIR");

			    // Verificar si la lista de chairs no está vacía
			    if (!chairs.isEmpty()) {
			        // Buscar el usuario con el id proporcionado dentro de la lista de chairs
			        for (Usuario chair : chairs) {
			            if (chair.getId_usuarios() == id) {
			                // Asignar el usuario encontrado como chair de la conferencia
			                conferencia.setChair(chair);
			                break;
			            }
			        }
			    }
			conferenciaRepository.save(conferencia);
		}
		
		
		public void AsignarChair(int id, Conferencia conferencia) {
			
			  List<Usuario> chairs = usuarioRepository.findByRolNombre("CHAIR");

			    // Verificar si la lista de chairs no está vacía
			    if (!chairs.isEmpty()) {
			        // Buscar el usuario con el id proporcionado dentro de la lista de chairs
			        for (Usuario chair : chairs) {
			            if (chair.getId_usuarios() == id) {
			                // Asignar el usuario encontrado como chair de la conferencia
			                conferencia.setChair(chair);
			                break;
			            }
			        }
			    }
			
			conferenciaRepository.save(conferencia);
			
		}
		
		//obtener Conferencias Chair
		public List<Conferencia> getConferenciasChair(Integer idChair){
			return conferenciaRepository.findByChair(idChair);	
		}
		
		
		//eliminar conferencias
		public void delete(Integer id) {
			conferenciaRepository.deleteById(id);
		}
		
		//editar Conferencias
		public void editarConferencia(Conferencia conferencia) {
			conferenciaRepository.save(conferencia);
		}
		
		

}
