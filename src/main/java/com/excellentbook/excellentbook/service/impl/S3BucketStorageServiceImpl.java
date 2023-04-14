package com.excellentbook.excellentbook.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.excellentbook.excellentbook.exception.AmazonS3UploadException;
import com.excellentbook.excellentbook.service.S3BucketStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class S3BucketStorageServiceImpl implements S3BucketStorageService {
    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${application.bucket.name}")
    private String bucketName;

    @Override
    public void uploadFile(String path, MultipartFile file) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/png");
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucketName, path, file.getInputStream(), metadata);
        } catch (AmazonServiceException | IOException e) {
            log.error("Failed to upload the file: {}", e.getMessage());
            throw new AmazonS3UploadException("Failed to upload the file");
        }
    }

}
