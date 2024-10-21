package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Conferencia;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ConferenciaRepository;
import com.example.demo.repository.UsuarioRepository;

@Service
public class ConferenciaService {
	
	@Autowired
	ConferenciaRepository conferenciaRepository;
	
	@Autowired
	AWSS3ServiceImpl awss3ServiceImpl;
	
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
			conferencia.setEstado("Activa");
			conferenciaRepository.save(conferencia);
		}
		
		
		//inactivar una conferencia, se utiliza ya que eliminarla causa problemas de integridad
		public void inactivarConferencia(int idConferencia) {
			Conferencia conferencia = conferenciaRepository.findById(idConferencia)
			        .orElseThrow(() -> new RuntimeException("Conferencia no encontrada"));
			try {
			 if(conferencia.getEstado().equals("Activa")) {
				 conferencia.setEstado("Inactiva");
				 conferenciaRepository.save(conferencia);
			 }else if(conferencia.getEstado().equals("Inactiva")){
				 conferencia.setEstado("Activa");
				 conferenciaRepository.save(conferencia);
			 }else {
				 throw new Exception("Ha ocurrido un error");
			 }
			}catch (Exception e) {
			    System.out.println(e.getMessage());
			}
			 
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
		public void editarConferencia(Conferencia conferencia,Integer idConferencia,MultipartFile archivoImagen) {
			Conferencia conferenciaAct = conferenciaRepository.findById(idConferencia)
			        .orElseThrow(() -> new RuntimeException("Conferencia no encontrada"));
			 // Actualizar los atributos de la conferencia
		    conferenciaAct.setDescripcion(conferencia.getDescripcion());
		    conferenciaAct.setNombre(conferencia.getNombre());
		    conferenciaAct.setLugar(conferencia.getLugar());
		    conferenciaAct.setFecha_inicio(conferencia.getFecha_inicio());
		    conferenciaAct.setFecha_fin(conferencia.getFecha_fin());

		    // Manejar la imagen si se proporciona
		    if (archivoImagen != null && !archivoImagen.isEmpty()) {
		        String imagenUrlAntigua = conferenciaAct.getImagenUrl();
		        if (imagenUrlAntigua != null && !imagenUrlAntigua.isEmpty()) {
		            String objectKeyAntiguo = imagenUrlAntigua.substring(imagenUrlAntigua.lastIndexOf("/") + 1);
		            awss3ServiceImpl.deleteObjectFromS3(objectKeyAntiguo);
		        }
		        // Subir la nueva imagen y obtener la nueva URL
		        String nuevaImagenUrl = awss3ServiceImpl.uploadFile(archivoImagen);
		        conferenciaAct.setImagenUrl(nuevaImagenUrl);
		    }

		    // Guardar la conferencia actualizada
		    conferenciaRepository.save(conferenciaAct);
		}
		
		

}
