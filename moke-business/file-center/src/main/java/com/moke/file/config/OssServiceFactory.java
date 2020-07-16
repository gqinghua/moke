package com.moke.file.config;

import com.moke.file.model.FileType;
import com.moke.file.service.IFileService;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.EnumMap;
import java.util.Map;

/**
 * FileService工厂<br>
 * 将各个实现类放入map
 *
 * @author
 */
@Configuration
public class OssServiceFactory {

    private Map<FileType, IFileService> map = new EnumMap<>(FileType.class);
    @Resource(name = "aliyunOssServiceImpl")
    private IFileService aliyunOssServiceImpl;

    @Resource(name = "qiniuOssServiceImpl")
    private IFileService qiniuOssServiceImpl;

    @PostConstruct
    public void init() {
        map.put(FileType.ALIYUN, aliyunOssServiceImpl);
        map.put(FileType.QINIU, qiniuOssServiceImpl);
    }

    public IFileService getFileService(String fileType) {
        return map.get(FileType.valueOf(fileType));
    }
}
