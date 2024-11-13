package com.example.demo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="usuarios")

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_usuarios")
public class Usuario implements Serializable {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_usuarios;
	private String tipoDocumento;
	private String documento;
	private String nombre;
	private String apellido;
	private String correo;
	private String password;
	
	 @ManyToOne
	 @JoinColumn(name = "id_rol")
	 private Rol rol;
	 
	 
	 @ManyToMany(mappedBy = "usuarios")
	 @JsonIgnore
	 private List<Comite> comites;
	 
	 @OneToMany(mappedBy = "asistente")
	 @JsonBackReference // Gestiona la serialización de las inscripciones
	 @JsonIgnore
	 private List<Inscripcion> inscripciones;
	 
	 @ManyToOne
	 @JoinColumn(name = "id_ciudad")
	 private Ciudad ciudad;
	 
	 @ManyToOne
	 @JoinColumn(name ="id_institucion")
	 private Institucion institucion;
	 
	 @OneToMany(mappedBy = "evaluador")
	 @JsonIgnore
	 private List<Evaluacion> evaluaciones;
	 
	 
	 // Relación uno-a-muchos con Articulos
	 @OneToMany(mappedBy = "autor")
	 @JsonIgnore
	 private List<Articulo> articulos;
	 
	 
	 
	 
}
