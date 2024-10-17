package com.example.demo.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.UsuarioService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	private JavaMailSender emailSender;

	// listar todos
	@GetMapping
	public List<Usuario> getAll() {
		return usuarioService.getStudent();
	}

	@GetMapping("/{id}")
	public Usuario getByid(@PathVariable Integer id) {
		Optional<Usuario> usuario = usuarioService.getUsuario(id);
		return usuario.get();
	}
	

	@DeleteMapping("/{id}")
	public void eliminarUsuario(@PathVariable Integer id) {
		usuarioService.deleteUsuarios(id);
	}
	

	@PostMapping("/save")
	public void save(@RequestBody Usuario usuario) {
		usuarioService.Guardar(usuario);
	}

	@PostMapping("/register/{idRol}/ciudad/{idCiudad}/institucion/{idInstitucion}")
	public ResponseEntity<String> register(@RequestBody Usuario usuario, @PathVariable int idRol,@PathVariable int idCiudad,
			@PathVariable int idInstitucion) {
		try {
			// Registrar el usuario
			usuarioService.RegistrarUsuario(usuario, idRol,idCiudad,idInstitucion);
		} catch (Exception e) {
			// Manejar otras excepciones
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Ocurrió un error durante el registro: " + e.getMessage());
		}
		// Obtener el usuario registrado
		Optional<Usuario> usuarioOpt = usuarioService.getCorreo(usuario.getCorreo());

		if (usuarioOpt.isPresent()) {
			Usuario usuarioCorreo = usuarioOpt.get();
			// Enviar correo de confirmación
			try {
				// Construir el contenido HTML del correo
				String htmlBody = "<html><body style='text-align: center;'>" + "<h2>" + usuarioCorreo.getNombre() + " "
						+ usuarioCorreo.getApellido() + "</h2>" + "<p>Felicitaciones, su registro como "
						+ usuarioCorreo.getRol().getNombre() + " ha sido exitoso en la plataforma de conferencias.</p>"
						+ "<p>Credenciales de acceso:</p>" + "<p>Correo: " + usuarioCorreo.getCorreo() + "</p>"
						+ "<p>Password:" + usuarioCorreo.getPassword() + "</p>" + // Aquí deberías manejar la seguridad
																					// de la contraseña
						"<img src='https://upload.wikimedia.org/wikipedia/commons/0/03/UFPS_Logo.png' alt='Logo UFPS' style='width: 100px; height: auto;' />"
						+ "</body></html>";
				// Crear MimeMessage y MimeMessageHelper para enviar correo HTML
				MimeMessage mimeMessage = emailSender.createMimeMessage();
				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				mimeMessageHelper.setFrom("sistemagestionconferenciasufps@gmail.com");
				mimeMessageHelper.setTo(usuarioCorreo.getCorreo());
				mimeMessageHelper.setSubject(usuarioCorreo.getNombre() + " " + usuarioCorreo.getApellido()
						+ " Bienvenido al Sistema gestor de conferencias");
				mimeMessageHelper.setText(htmlBody, true); // true indica que el contenido es HTML

				emailSender.send(mimeMessage);

			} catch (Exception e) {
				// Manejar errores al enviar el correo
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Registro exitoso, pero ocurrió un error al enviar el correo: " + e.getMessage());
			}
		}

		return ResponseEntity.status(HttpStatus.CREATED).body("Registro exitoso y correo enviado.");
	}

	@PostMapping("/enviarCorreo/{correo}")
	public void enviarCorreo(@PathVariable String correo) {
		Optional<Usuario> usuarioOpt = usuarioService.getCorreo(correo);
		if (usuarioOpt.isPresent()) {
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

			String bodyMessage = (usuarioOpt.get().getNombre()
					+ " Felicitaciones su registro ha sido exitoso en la plataforma de conferencias");
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(usuarioOpt.get().getCorreo());
			message.setFrom("sistemagestionconferenciasufps@gmail.com");
			message.setSubject("" + usuarioOpt.get().getNombre() + " " + usuarioOpt.get().getApellido()
					+ " Bienvenido al Sistema gestor de conferencias" + " ");
			message.setText(bodyMessage);

			emailSender.send(message);
		}
	}

	/**
	 * @PostMapping("/register/{idRol}") public void register(@RequestBody Usuario
	 * usuario,@PathVariable int idRol) { usuarioService.RegistrarUsuario(usuario,
	 * idRol); Optional<Usuario> usuarioOpt =
	 * usuarioService.getCorreo(usuario.getCorreo());
	 * 
	 * if (usuarioOpt.isPresent()) {
	 * 
	 * Usuario usuarioCorreo = usuarioOpt.get();
	 * 
	 * MimeMessage mimeMessage = emailSender.createMimeMessage(); MimeMessageHelper
	 * mimeMessageHelper = new MimeMessageHelper(mimeMessage);
	 * 
	 * String bodyMessage = (usuarioCorreo.getNombre()+ " Felicitaciones su registro
	 * ha sido exitoso en la plataforma de conferencias"); SimpleMailMessage message
	 * = new SimpleMailMessage(); message.setTo(usuarioCorreo.getCorreo());
	 * message.setFrom("neidersimarra3@gmail.com"); message.setSubject("" +
	 * usuarioCorreo.getNombre() + " " + usuarioCorreo.getApellido() + " Bienvenido
	 * al Sistema gestor de conferencias" + " " ); message.setText(bodyMessage);
	 * 
	 * emailSender.send(message);
	 * 
	 * 
	 * } }
	 */

	@GetMapping("/findUsuarios/{rol}")
	public List<Usuario> getUsuariosPorRol(@PathVariable("rol") String nombre) {
		return usuarioService.getEvaluadores(nombre);
	}
	

	@GetMapping("/findCorreo/{correo}")
	public Optional<Usuario> getUsuariosPorCorreo(@PathVariable("correo") String correo) {
		return usuarioService.getCorreo(correo);
	}

	@PostMapping("/iniciarSesion")
	public ResponseEntity<Usuario> iniciarSesion(@RequestBody Usuario login) {
		return new ResponseEntity<>(usuarioService.iniciarSesion(login), HttpStatus.OK);

	}

}
