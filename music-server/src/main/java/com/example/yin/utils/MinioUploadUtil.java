package com.example.yin.utils;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
@Slf4j
public class MinioUploadUtil {


    @Value("${minio.bucket-name}")
    public static String bucketName;

    @Value("${minio.endpoint}")
    private static String endpoint;

    @Value("${minio.access-key}")
    private static String accessKey;

    @Value("${minio.secret-key}")
    private static String secretKey;

    public static MinioClient minioClient;

    public static void init(){
        minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey,secretKey).build();
    }

    public static String uploadFile(MultipartFile file){
        try {
            init();
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(file.getOriginalFilename())
                    .stream(inputStream,inputStream.available(),-1)
                    .contentType(file.getContentType()).build());
            return "File successfully uploaded";
        } catch (Exception e) {
            e.printStackTrace();
            return "File upload failed" + e.getMessage();
        }
    }


    public static String uploadSongImgFile(MultipartFile file) {
        try {
            init();
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object("/singer/song/"+file.getOriginalFilename())
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            return "File uploaded successfully!";
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return "Error uploading file to MinIO: " + e.getMessage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
