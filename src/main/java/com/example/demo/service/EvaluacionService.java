package com.example.demo.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

import jakarta.mail.internet.MimeMessage;
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
	
	@Autowired
	private JavaMailSender emailSender;
	
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
			
			 public Evaluacion cambiarEstado(int id, String nuevoEstado,String comentario) {
			        Optional<Evaluacion> optionalEvaluacion = evaluacionRepository.findById(id);
			        if (optionalEvaluacion.isPresent()) {
			            Evaluacion evaluacion = optionalEvaluacion.get();
			            evaluacion.setEstado(nuevoEstado);
			            evaluacion.setComentario(comentario);
			            optionalEvaluacion.get().getArticulo().setEstado(nuevoEstado);
			            return evaluacionRepository.save(evaluacion);
			        } else {
			            throw new EntityNotFoundException("Evaluación no encontrada");
			        }
			 }
			 
			 public List<Evaluacion> obtenerEvaluacionesByArticulo(int idArticulo){
				 return evaluacionRepository.findByArticuloId(idArticulo);
			 }
			 
			 public List<Evaluacion> getEvaluacionesConferencia(int idConferencia){
				 return evaluacionRepository.findByConferencia(idConferencia);
			 }
			 
			 
			 
			 
			 public ResponseEntity<String> asignarEvaluacion(int idEvaluador,int idArticulo , Evaluacion evaluacion) {
				 
				 Optional<Usuario> opEvaluador = usuarioRepository.findById(idEvaluador);
				 Optional<Articulo> opArticulo = articuloRepository.findById(idArticulo);
				 
				 
				 if(opEvaluador.isPresent() && opArticulo.isPresent() ) {
					 
					 Usuario evaluador = opEvaluador.get();
					 
					 Articulo articulo = opArticulo.get();
					 
					 evaluacion.setEvaluador(evaluador);
					 evaluacion.setArticulo(articulo);
					 articulo.setEstado("Pendiente");
					 
					 try {
							// Construir el contenido HTML del correo
							String htmlBody = "<html><body style='text-align: center;'>" + "<h2>" + evaluador.getNombre() + " "
									+ evaluador.getApellido() + "</h2>" + "<p>Has sido seleccionado como "
									+ evaluador.getRol().getNombre() + ".</p>"
									+ "<p>Articulo a evaluar:</p>"  + articulo.getNombre() + "</p>"
									+ "<p>Fecha limite" + evaluacion.getFechaHora() + "</p>" + // Aquí deberías manejar la seguridad
																								// de la contraseña
									"<img src='https://upload.wikimedia.org/wikipedia/commons/0/03/UFPS_Logo.png' alt='Logo UFPS' style='width: 100px; height: auto;' />"
									+ "</body></html>";
							// Crear MimeMessage y MimeMessageHelper para enviar correo HTML
							MimeMessage mimeMessage = emailSender.createMimeMessage();
							MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
							mimeMessageHelper.setFrom("sistemagestionconferenciasufps@gmail.com");
							mimeMessageHelper.setTo(evaluador.getCorreo());
							mimeMessageHelper.setSubject(evaluador.getNombre() + " " + evaluador.getApellido()
									+ "Te han seleccionado como evaluador en la conferencia" + " " + articulo.getConferencia().getNombre());
							mimeMessageHelper.setText(htmlBody, true); // true indica que el contenido es HTML

							emailSender.send(mimeMessage);

						} catch (Exception e) {
							// Manejar errores al enviar el correo
							return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
									.body("Registro exitoso, pero ocurrió un error al enviar el correo: " + e.getMessage());
						}
					 
					 evaluacionRepository.save(evaluacion);
				 }
				return null;
				 		 
			 }
			
			
			
			

}
