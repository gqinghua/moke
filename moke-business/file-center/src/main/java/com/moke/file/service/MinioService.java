package com.moke.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @program: sw
 * @ClassName MinioService
 * @description: [用一句话描述此类]
 * @author: GUO
 * @create: 2020-06-08 23:12
 **/
public interface MinioService  {
    public String uploadToMinio(String bucketName, String fileName, MultipartFile file) throws IOException;
}
