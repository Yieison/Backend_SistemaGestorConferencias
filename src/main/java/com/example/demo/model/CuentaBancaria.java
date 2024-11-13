package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class CuentaBancaria {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String numeroCuenta; // Número de cuenta
    private String banco; // Nombre del banco
    private String titular; // Nombre del titular de la cuenta

    @OneToOne(mappedBy = "cuentaBancaria")
    private Conferencia conferencia; // Relación con la conferencia

}
