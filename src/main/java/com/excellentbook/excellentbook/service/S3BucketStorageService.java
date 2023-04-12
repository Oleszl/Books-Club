package com.excellentbook.excellentbook.service;


import org.springframework.web.multipart.MultipartFile;

public interface S3BucketStorageService {
    public void uploadFile(String path, MultipartFile file);
}
