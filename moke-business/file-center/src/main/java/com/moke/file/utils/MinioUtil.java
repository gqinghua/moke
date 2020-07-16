package com.moke.file.utils;

import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.InputStream;

/**
 * @program: sw
 * @ClassName MinioUtil
 * @description: [用一句话描述此类]
 * @author: GUO
 * @create: 2020-06-08 23:10
 **/
@Slf4j
@Component
public class MinioUtil implements InitializingBean {

    @Value("${minio.AccessKey}")
    private String accessKey;

    @Value("${minio.SecretKey}")
    private String secretKey;

    @Value("${minio.BucketName}")
    private String bucketName;

    @Value("${minio.S3Address}")
    private String s3Address;

    private MinioClient minioClient;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.minioClient = new MinioClient(this.s3Address, this.accessKey, this.secretKey);
    }

    public String upload(String bucketName, String fileName, InputStream sourceStream) {
        if (!StringUtils.hasText(bucketName)) {
            bucketName = this.bucketName;
        }
        String fileUrl;
        try {
            // if bucket is not exist, create it!
            if (!minioClient.bucketExists(bucketName)) {
                minioClient.makeBucket(bucketName);
                log.info("[MinioUtil-upload]: create Bucket : " + bucketName);
            }
            fileUrl = minioClient.presignedGetObject(bucketName, fileName);
            // upload
            minioClient.putObject(bucketName, fileName, sourceStream, sourceStream.available(), "UTF-8");
        } catch (Exception e) {
            log.error("[MinioUtil-upload]: upload to minio failed!");
            fileUrl = null;
        }
        return fileUrl;
    }
}