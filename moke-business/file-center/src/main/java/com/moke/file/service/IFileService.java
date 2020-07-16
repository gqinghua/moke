package com.moke.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moke.common.model.PageResult;
import com.moke.file.model.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 文件service 目前仅支持阿里云oss,七牛云
 *
 * @author 作者
*/

public interface IFileService extends IService<FileInfo> {
	FileInfo upload(MultipartFile file ) throws Exception;
	
	PageResult<FileInfo> findList(Map<String, Object> params);
}
