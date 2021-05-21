package com.joaolucas.cursomc.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${aws.access_key_id}")
    private String awsId;

    @Value("${aws.secret_access_key}")
    private String awsKey;

    @Bean
    public AmazonS3 s3Client() {
        var awsCredentials = new BasicAWSCredentials(awsId, awsKey);
        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.SA_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
