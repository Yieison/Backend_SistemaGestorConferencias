package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Ciudad;
import com.example.demo.model.Institucion;
import com.example.demo.model.Pais;
import com.example.demo.repository.CiudadRepository;
import com.example.demo.repository.InstitucionRepository;
import com.example.demo.repository.PaisRepository;

@Service
public class UbicacionService {
	
	@Autowired
	PaisRepository paisRepository;
	
	@Autowired
	CiudadRepository ciudadRepository;
	
	@Autowired
	InstitucionRepository institucionRepository;
	
	public List<Pais> getAllPaises(){
		return paisRepository.findAll();
	}
	
	public List<Ciudad> getAllCiudades(){
		return ciudadRepository.findAll();
	}
	
	public List<Institucion> getAllInstituciones(){
		return institucionRepository.findAll();
	}
	
	public List<Institucion> getInstitucionesByCiudad(int ciudad){
		return institucionRepository.findByCiudadId(ciudad);
	}
	
    public List<Ciudad> getCiudadesByPais(int idPais){
    	return ciudadRepository.findByPaisId(idPais);
    }
	
	

}
