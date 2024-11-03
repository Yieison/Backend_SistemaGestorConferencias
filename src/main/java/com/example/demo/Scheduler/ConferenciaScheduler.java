package com.example.demo.Scheduler;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.service.ConferenciaService;

@Service
public class ConferenciaScheduler {
	
	
	   @Autowired
	    private ConferenciaService conferenciaService;

	    @Scheduled(cron = "0 0 0 * * *") // Se ejecuta todos los días a medianoche
	    public void desactivarConferenciasPasadas() {
	        LocalDate today = LocalDate.now();
	        
	        // Llama al servicio para desactivar conferencias cuya fecha de fin ha pasado
	        conferenciaService.desactivarConferenciasConFechaFinAntesDe(today);
	    }

}
