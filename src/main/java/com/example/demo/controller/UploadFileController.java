package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import com.example.demo.AWS.AWSS3Service;

@RestController
@RequestMapping("/s3")
public class UploadFileController {
	
	
	@Autowired
	private AWSS3Service awss3Service;
	
	
	@PostMapping("/uploadFile")
	public ResponseEntity<String> uploadFile(@RequestPart(value="file") MultipartFile file){
		awss3Service.uploadFile(file);
		String response = "El archivo" + file.getOriginalFilename() + "fue cargado correctamente";
		return new  ResponseEntity<String>(response,HttpStatus.OK);
	}
	/**
	//lista por nombre
	@GetMapping("/list")
	public ResponseEntity<List<String>> listFiles() {
		return new ResponseEntity<List<String>>(awss3Service.getObjectFromS3(), HttpStatus.OK);
	}
	*/
	
	@GetMapping("/list")
	public ResponseEntity<List<String>> listFiles() {
	    List<String> urls = awss3Service.getObjectFromS3();
	    return new ResponseEntity<>(urls, HttpStatus.OK);
	}
	
	
	@GetMapping("/download")
	public ResponseEntity<Resource> download(@RequestParam("key") String key){
		InputStreamResource resource  = new InputStreamResource(awss3Service.downloadFile(key));
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+key+"\"").body(resource);
	}
	
	
	
	
}


