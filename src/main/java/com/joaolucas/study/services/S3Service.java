package com.joaolucas.study.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.joaolucas.study.services.exceptions.FileException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 s3Client;

    @Value("${s3.bucket}")
    private String bucketName;

    public URI uploadFile(MultipartFile multipartFile) {
        try {
            var fileName = multipartFile.getOriginalFilename();
            InputStream inputStream = null;
            inputStream = multipartFile.getInputStream();
            var contentType = multipartFile.getContentType();
            return uploadFile(inputStream, fileName, contentType);
        } catch (IOException e) {
            throw new FileException("Erro de IO: " + e.getMessage());
        }
    }

    public URI uploadFile(InputStream is, String fileName, String contentType) {
        try {
            var meta = new ObjectMetadata();
            meta.setContentType(contentType);
            log.info("Iniciando upload");
            s3Client.putObject(bucketName, fileName, is, meta);
            log.info("Upload finalizado");
            return s3Client.getUrl(bucketName, fileName).toURI();
        } catch (URISyntaxException e) {
            throw new FileException("Erro ao converter URL para URI");
        }
    }
}
