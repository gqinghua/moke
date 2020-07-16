package com.moke.file.service.impl;

import com.moke.file.service.MinioService;
import com.moke.file.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @program: sw
 * @ClassName MinioService
 * @description: [用一句话描述此类]
 * @author: GUO
 * @create: 2020-06-08 23:12
 **/
@Service
@Slf4j
public class MinioServiceImpl implements MinioService {

    @Autowired
    MinioUtil minioUtil;

    @Override
    public String uploadToMinio(String bucketName, String fileName, MultipartFile file) throws IOException {
        return minioUtil.upload(bucketName, fileName, file.getInputStream());
    }
}
