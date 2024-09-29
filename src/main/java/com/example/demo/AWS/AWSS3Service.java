package com.example.demo.AWS;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface AWSS3Service {
	
	
	//Cargar los archivos al bucket S3
	String uploadFile(MultipartFile file);
	
	//listar archivos del bucket
	List<String> getObjectFromS3();
	
	//descargar archivos del bucket
	InputStream downloadFile(String key);

	/**
	@Override
	public List<String> getObjectFromS3() {
		//traerme una lista de objects del buckets
		 ListObjectsV2Result result = amazonS3.listObjectsV2(bucketName);
		 List<S3ObjectSummary> objects = result.getObjectSummaries();
		 //convertir los objetos a una lista String
		 List<String> list = objects.stream().map(item ->{
			 return  item.getKey();
		 }).collect(Collectors.toList());
		 
		 
		return list;
	}
	*/
	//obtener URL del ultimo archivo g
	String getLastUploadedFileUrl();

	void deleteObjectFromS3(String objectKey);

	String uploadFileCarpeta(MultipartFile file, String folder);
}
