package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 
 * @author HaiYu
 */
public interface VideoService {
    Map<String, Object> uploadVideo(MultipartFile file);

    void deleteVideo(String videoId);

    String getVideoPlayAuth(String videoId);
}
