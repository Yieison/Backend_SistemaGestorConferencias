package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Pago;
import com.example.demo.service.AWSS3ServiceImpl;
import com.example.demo.service.PagoService;

@RestController
@RequestMapping("/pagos")
public class PagoController {
	
	@Autowired
	PagoService pagoService;
	
	@Autowired
	AWSS3ServiceImpl awss3ServiceImpl;
	
	@GetMapping
	public List<Pago> getAllPagos(){
		return pagoService.getPagos();
	}
	
	@GetMapping("/estado/{estado}")
	public List<Pago> getPagoEstado(@PathVariable String estado){
		return pagoService.getPagosEstado(estado);
	}
	
	@PostMapping("/savePago/{idInscripcion}")
	public void guardarPago(@RequestPart("file") MultipartFile file,@RequestPart("pago") Pago pago,@PathVariable int idInscripcion) {
	    String folder = "pagos";
	    String fileUrl = awss3ServiceImpl.uploadFileCarpeta(file, folder);
	    pago.setUrlSoporte(fileUrl);
	    pagoService.realizarPago(pago, idInscripcion);
	}
	
	@PostMapping("/uploadFile")
	public String uploadFile(@RequestPart("file") MultipartFile file) {
	    String folder = "pagos";
	    return awss3ServiceImpl.uploadFileCarpeta(file, folder);
	}



}
