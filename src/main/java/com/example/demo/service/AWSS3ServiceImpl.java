package com.example.demo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.demo.AWS.AWSS3Service;


@Service
public class AWSS3ServiceImpl implements AWSS3Service {
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AWSS3ServiceImpl.class);
	
	//instanciar el bean
	@Autowired
	private AmazonS3 amazonS3;
	
	@Value("${aws.s3.bucket}")
	private String bucketName; 
	
	
	
	 @Override
	    public String uploadFile(MultipartFile file) {
	        File mainFile = new File(file.getOriginalFilename());
	        try (FileOutputStream stream = new FileOutputStream(mainFile)) {
	            stream.write(file.getBytes());
	            String newFileName = System.currentTimeMillis() + "_" + mainFile.getName();
	            LOGGER.info("Subiendo archivo con el nombre " + newFileName);

	            PutObjectRequest request = new PutObjectRequest(bucketName, newFileName, mainFile);
	            amazonS3.putObject(request);

	            // Devolver la URL del archivo subido
	            return amazonS3.getUrl(bucketName, newFileName).toString();
	        } catch (IOException e) {
	            LOGGER.error(e.getMessage(), e);
	            throw new RuntimeException("Error al subir el archivo a S3", e);
	        }
	    }
	 
	 
	 @Override
	 public String uploadFileCarpeta(MultipartFile file, String folder) {
	     File mainFile = new File(file.getOriginalFilename());
	     try (FileOutputStream stream = new FileOutputStream(mainFile)) {
	         stream.write(file.getBytes());
	         
	         // Asegurarse de que la carpeta termina con "/"
	         if (!folder.endsWith("/")) {
	             folder += "/";
	         }

	         // Concatenar la carpeta al nombre del archivo
	         String newFileName = folder + System.currentTimeMillis() + "_" + mainFile.getName();
	         LOGGER.info("Subiendo archivo a la carpeta '" + folder + "' con el nombre " + newFileName);

	         // Crear una solicitud para subir el archivo a S3
	         PutObjectRequest request = new PutObjectRequest(bucketName, newFileName, mainFile);
	         amazonS3.putObject(request);

	         // Devolver la URL del archivo subido
	         return amazonS3.getUrl(bucketName, newFileName).toString();
	     } catch (IOException e) {
	         LOGGER.error(e.getMessage(), e);
	         throw new RuntimeException("Error al subir el archivo a S3", e);
	     }
	 }


  
	
	
	/**
	@Override
	public void uploadFile(MultipartFile file) {
		//convertir el multipartfile en un file
		File mainFile=new File(file.getOriginalFilename());
		
		try(FileOutputStream stream=new FileOutputStream(mainFile)){
			//copiar en el stream contenido del m
			stream.write(file.getBytes());
			String newFileName = System.currentTimeMillis() + "_" + mainFile.getName();
			LOGGER.info("subiendo archivo con el nombre" + newFileName);
			//Armar la solicitud
			PutObjectRequest request = new PutObjectRequest(bucketName,newFileName,mainFile);
			amazonS3.putObject(request);
		} catch(IOException e) {
			LOGGER.error(e.getMessage(),e);
		}
			
		
		
	}
	*/
	

	 //metodo para obtener las urls
	@Override
	public List<String> getObjectFromS3() {
	    ListObjectsV2Result result = amazonS3.listObjectsV2(bucketName);
	    List<S3ObjectSummary> objects = result.getObjectSummaries();
	    
	    List<String> urls = new ArrayList<>();
	    
	    // Construir URLs completas para las imágenes
	    for (S3ObjectSummary object : objects) {
	        String key = object.getKey();
	        String url = amazonS3.getUrl(bucketName, key).toString();
	        urls.add(url);
	    }
	    
	    return urls;
	}

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


	@Override
	public InputStream downloadFile(String key) {
		S3Object object = amazonS3.getObject(bucketName,key);
		return object.getObjectContent();
	}
	
	 @Override
	    public String getLastUploadedFileUrl() {
	        ListObjectsV2Result result = amazonS3.listObjectsV2(bucketName);
	        List<S3ObjectSummary> objects = result.getObjectSummaries();

	        if (objects.isEmpty()) {
	            return null;
	        }

	        // Ordenar los objetos por la fecha de modificación
	        objects.sort((o1, o2) -> o2.getLastModified().compareTo(o1.getLastModified()));

	        // Obtener la URL del archivo más reciente
	        String key = objects.get(0).getKey();
	        return amazonS3.getUrl(bucketName, key).toString();
	    }
	 
	 
	 //metodo para eliminar 
	 @Override
	 public void deleteObjectFromS3(String objectKey) {
	     try {
	         amazonS3.deleteObject(bucketName, objectKey);
	         System.out.println("Archivo eliminado exitosamente: " + objectKey);
	     } catch (AmazonServiceException e) {
	         // Manejo de errores de Amazon S3
	         System.err.println(e.getErrorMessage());
	     } catch (SdkClientException e) {
	         // Manejo de errores del cliente SDK
	         System.err.println(e.getMessage());
	     }
	 }

	
	

}
