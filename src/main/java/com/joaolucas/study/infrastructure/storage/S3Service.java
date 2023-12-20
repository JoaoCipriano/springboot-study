package com.joaolucas.study.infrastructure.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.joaolucas.study.domain.exceptions.FileException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 s3Client;
    private final Logger logger;

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
            logger.info("Starting upload");
            s3Client.putObject(bucketName, fileName, is, meta);
            logger.info("Upload finalized");
            return s3Client.getUrl(bucketName, fileName).toURI();
        } catch (URISyntaxException e) {
            throw new FileException("Error to convert URL to URI");
        }
    }
}
