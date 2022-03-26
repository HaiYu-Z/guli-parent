package com.atguigu.oss.controller;

import com.atguigu.base.entity.Result;
import com.atguigu.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author HaiYu
 */
@Api(description = "文件管理")
@RestController
@RequestMapping("/oss/file")
public class FileController {
    @Autowired
    FileService fileService;

    @ApiOperation("上传文件")
    @PostMapping("/uploadFile")
    public Result uploadFile(MultipartFile file) {
        try {
            String url = fileService.uploadFile(file);
            return Result.ok().data("url", url);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }
}
