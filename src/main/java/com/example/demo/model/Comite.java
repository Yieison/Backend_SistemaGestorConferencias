package com.example.demo.model;

import java.io.Serializable;
import java.util.List;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Comite implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name ="nombre")
	private String nombre;
	
	@ManyToOne
    @JoinColumn(name = "conferencia_id")
	//@JsonIgnoreProperties("comites") // Ignora la propiedad "comites" al serializar "conferencia"
    private Conferencia conferencia;

    @ManyToMany
    @JoinTable(
        name = "comite_usuario",
        joinColumns = @JoinColumn(name = "comite_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> usuarios;
    
    public Comite() {}

}