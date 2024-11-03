package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.Articulo;
import com.example.demo.model.Conferencia;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ArticuloRepository;
import com.example.demo.repository.ConferenciaRepository;
import com.example.demo.repository.UsuarioRepository;

import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class ArticuloService {
	
	@Autowired
	ArticuloRepository articuloRepository;
	
	@Autowired
	ConferenciaRepository conferenciaRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	private JavaMailSender emailSender;
	
	public List<Articulo> getArticulo(){
		return articuloRepository.findAll();
	}
	
	//traer todos los articulos por estado
	public List<Articulo> getArticuloEstado(String estado){
		return articuloRepository.findByEstado(estado);
	}
	
	public List<Articulo> getArticulosAutor(int idAutor){
		return articuloRepository.findArticulosByAutorId(idAutor);
	}
	
	public List<Articulo> getArticuloConferencia(int idConferencia){
		return articuloRepository.findArticulosByConferencia(idConferencia);
	}
	
	
	 public void decidirEstadoFinalArticulo(int idArticulo, String decisionFinal) {
		    // Obtener el artículo por id
		    Articulo articulo = articuloRepository.findById(idArticulo)
		                              .orElseThrow(() -> new RuntimeException("Artículo no encontrado"));

		    // enviar Notificacion al autor
		    Usuario usuarioCorreo = articulo.getAutor();
				// Enviar correo de confirmación
		    // Asignar la decisión final del chair
		    articulo.setEstado(decisionFinal);
		    
		    try {
		        // Construir el contenido HTML del correo con estilos en línea
		        String htmlBody = "<html>" +
		                "<body style='text-align: center; font-family: Arial, sans-serif; color: #333; background-color: #f4f4f4; padding: 20px;'>" +
		                "<div style='max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; padding: 20px; box-shadow: 0 4px 8px rgba(0,0,0,0.1);'>" +
		                "<h2 style='color: #d62828;'>" + usuarioCorreo.getNombre() + " " + usuarioCorreo.getApellido() + "</h2>" +
		                "<p style='font-size: 16px; line-height: 1.5;'>Su artículo <strong>" + articulo.getNombre() + "</strong> ha sido evaluado.</p>" +
		                "<p style='font-size: 16px; line-height: 1.5;'>El resultado de la evaluación del artículo ha sido: <strong>" + articulo.getEstado() + "</strong></p>" +
		                "<p style='font-size: 16px; line-height: 1.5; color: #555;'>Ingresa a la plataforma para continuar con la preparación de tu presentación.</p>" +
		                "<a href='https://sistema-gestor-conferencias.vercel.app/' style='display: inline-block; padding: 10px 20px; margin-top: 20px; background-color: #d62828; color: #ffffff; text-decoration: none; border-radius: 5px; font-weight: bold;'>Ir a la Plataforma</a>" +
		                "<hr style='border: 0; height: 1px; background-color: #ddd; margin: 20px 0;'>" +
		                "<img src='https://upload.wikimedia.org/wikipedia/commons/0/03/UFPS_Logo.png' alt='Logo UFPS' style='width: 100px; height: auto; margin-top: 20px;' />" +
		                "</div>" +
		                "</body>" +
		                "</html>";

		        // Crear MimeMessage y MimeMessageHelper para enviar correo HTML
		        MimeMessage mimeMessage = emailSender.createMimeMessage();
		        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		        mimeMessageHelper.setFrom("sistemagestionconferenciasufps@gmail.com");
		        mimeMessageHelper.setTo(usuarioCorreo.getCorreo());
		        mimeMessageHelper.setSubject(usuarioCorreo.getNombre() + " " + usuarioCorreo.getApellido()
		                + " su artículo enviado a la conferencia " + articulo.getConferencia().getNombre() + " ya ha sido evaluado");
		        mimeMessageHelper.setText(htmlBody, true); // true indica que el contenido es HTML

		        emailSender.send(mimeMessage);
		    } catch (Exception e) {
		        // Manejar errores al enviar el correo
		        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                .body("Registro exitoso, pero ocurrió un error al enviar el correo: " + e.getMessage());
		    }



		    // Guardar la decisión final
		    articuloRepository.save(articulo);
		}
	
	
	public void saveArticulo(int idConferencia,int idAutor,Articulo articulo) {
		Optional<Conferencia> opConferencia = conferenciaRepository.findById(idConferencia);
		Optional<Usuario> opAutor = usuarioRepository.findById(idAutor);
		if(opConferencia.isPresent() && opAutor.isPresent()) {
			Conferencia conferencia = opConferencia.get();
			Usuario autor = opAutor.get();
			articulo.setConferencia(conferencia);
			articulo.setAutor(autor);
			articulo.setEstado("Enviado");
			articuloRepository.save(articulo);
		}
		
	}
	

}
