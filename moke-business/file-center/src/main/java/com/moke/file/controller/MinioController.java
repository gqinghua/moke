package com.moke.file.controller;

import com.moke.file.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @program: sw
 * @ClassName MinioController
 * @description: [用一句话描述此类]
 * @author: GUO
 * @create: 2020-06-08 23:11
 **/
@RestController
public class MinioController {

    @Autowired
    MinioService minioService;

    @PostMapping(value = "/minio/file/upload")
    public String upload(@RequestParam String bucketName,
                         @RequestParam String fileName,
                         @RequestParam MultipartFile file) throws IOException {
        return minioService.uploadToMinio(bucketName, fileName, file);
    }
}