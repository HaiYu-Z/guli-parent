package com.atguigu.vod.controller;

import com.atguigu.base.entity.Result;
import com.atguigu.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 
 * @author HaiYu
 */
@Api(description = "视频管理")
@RestController
@RequestMapping("/vod/video")
public class VideoController {

    @Autowired
    VideoService videoService;

    @ApiOperation("上传视频")
    @PostMapping("/uploadVideo")
    public Result uploadVideo(MultipartFile file) {
        Map<String, Object> map = videoService.uploadVideo(file);
        return Result.ok().data(map);
    }


    @ApiOperation("删除视频")
    @DeleteMapping("/deleteVideo/{videoId}")
    public Result deleteVideo(@PathVariable String videoId) {
        videoService.deleteVideo(videoId);
        return Result.ok();
    }

    @ApiOperation("获取视频播放凭证")
    @GetMapping("/getVideoPlayAuth/{videoId}")
    public Result getVideoPlayAuth(@PathVariable String videoId) {
        String playAuth = videoService.getVideoPlayAuth(videoId);
        return Result.ok().data("playAuth",playAuth);
    }
}
