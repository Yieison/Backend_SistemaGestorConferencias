package com.example.demo.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Articulo;
import com.example.demo.model.Conferencia;
import com.example.demo.model.Evaluacion;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ArticuloRepository;
import com.example.demo.repository.ConferenciaRepository;
import com.example.demo.repository.EvaluacionRepository;
import com.example.demo.repository.UsuarioRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;


@Service
public class EvaluacionService {
	
	@Autowired
	EvaluacionRepository evaluacionRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	ConferenciaRepository conferenciaRepository;
	
	
	@Autowired
	ArticuloRepository articuloRepository;
	
	  @PersistenceContext
	    private EntityManager entityManager;
	
	//traer todos los estudiantes
		public List<Evaluacion> getEvaluacion(){
			return evaluacionRepository.findAll();
		}
		
		//traer estudiante por id
			public Optional<Evaluacion> getEvaluacionByID(Integer id) {
				return evaluacionRepository.findById(id);
			}
			
		

			public void Guardar(Evaluacion evaluacion) {
				evaluacionRepository.save(evaluacion);
			}
			
			public List<Evaluacion> getEvaluacion(String estado) {
				return evaluacionRepository.findByEstado(estado);
			}
			
			 public Evaluacion cambiarEstado(int id, String nuevoEstado) {
			        Optional<Evaluacion> optionalEvaluacion = evaluacionRepository.findById(id);
			        if (optionalEvaluacion.isPresent()) {
			            Evaluacion evaluacion = optionalEvaluacion.get();
			            evaluacion.setEstado(nuevoEstado);
			            return evaluacionRepository.save(evaluacion);
			        } else {
			            throw new EntityNotFoundException("Evaluación no encontrada");
			        }
			 }
			 
			 
			 public void asignarEvaluacion(int idEvaluador,int idArticulo , Evaluacion evaluacion) {
				 
				 Optional<Usuario> opEvaluador = usuarioRepository.findById(idEvaluador);
				 Optional<Articulo> opArticulo = articuloRepository.findById(idArticulo);
				 
				 
				 if(opEvaluador.isPresent() && opArticulo.isPresent() ) {
					 
					 Usuario evaluador = opEvaluador.get();
					 
					 Articulo articulo = opArticulo.get();
					 
					 evaluacion.setEvaluador(evaluador);
					 evaluacion.setArticulo(articulo);
					 
					 evaluacionRepository.save(evaluacion);
					
				 }
				 		 
			 }
			
			
			
			

}
