package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Conferencia;
import com.example.demo.model.Convocatoria;
import com.example.demo.repository.ConferenciaRepository;
import com.example.demo.repository.ConvocatoriaRepository;

@Service
public class ConvocatoriaService {
	
	@Autowired
	ConvocatoriaRepository convocatoriaRepository;
	
	@Autowired
	ConferenciaRepository conferenciaRepository;
	
	public List<Convocatoria> getConvocatorias(){
		return convocatoriaRepository.findAll();
	}
	
	public Optional<Convocatoria> getConvocatoriaById(int id) {
		return convocatoriaRepository.findById(id);
	}
	
	public void agregarConvocatoria(int idConferencia,Convocatoria convocatoria) {
		
		Conferencia conferencia = conferenciaRepository.findById(idConferencia).orElseThrow(() -> new RuntimeException("Conferencia no encontrada"));
		convocatoria.setConferencia(conferencia);
		
		convocatoriaRepository.save(convocatoria);
	}
	
	public Convocatoria editarConvocatoria(int idConvocatoria, Convocatoria nuevosDatos) {
	    // Buscar la convocatoria existente
	    Convocatoria convocatoriaExistente = convocatoriaRepository.findById(idConvocatoria)
	        .orElseThrow(() -> new RuntimeException("Convocatoria no encontrada"));

	    // Actualizar los campos de la convocatoria
	    convocatoriaExistente.setDescripcion(nuevosDatos.getDescripcion());
	    convocatoriaExistente.setFechaInicioEnvio(nuevosDatos.getFechaInicioEnvio());
	    convocatoriaExistente.setFechaLimiteEnvio(nuevosDatos.getFechaLimiteEnvio());
	    convocatoriaExistente.setFechaAceptacion(nuevosDatos.getFechaAceptacion());
	    convocatoriaExistente.setFechaPublicacion(nuevosDatos.getFechaPublicacion());
	    convocatoriaExistente.setFechaEnvioPublicaciones(nuevosDatos.getFechaEnvioPublicaciones());

	    // Puedes actualizar otros campos si es necesario (e.g. conferencia, si corresponde)

	    // Guardar los cambios en la base de datos
	    return convocatoriaRepository.save(convocatoriaExistente);
	}


}
