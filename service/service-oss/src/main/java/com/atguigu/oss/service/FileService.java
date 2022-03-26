package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author HaiYu
 */
public interface FileService {

    String uploadFile(MultipartFile file);
}
