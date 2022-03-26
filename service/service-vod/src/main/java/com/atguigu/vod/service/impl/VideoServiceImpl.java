package com.atguigu.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.base.handler.GuliException;
import com.atguigu.vod.service.VideoService;
import com.atguigu.vod.util.AliyunVodSDKUtils;
import com.atguigu.vod.util.ConstantPropertiesUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 
 * @author HaiYu
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Override
    public Map<String, Object> uploadVideo(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String title = Objects.requireNonNull(originalFilename).substring(0, originalFilename.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET,
                    title, originalFilename, inputStream
            );

            UploadVideoImpl uploadVideo = new UploadVideoImpl();
            UploadStreamResponse response = uploadVideo.uploadStream(request);
            String videoSourceId = response.getVideoId();

            if (videoSourceId == null) {
                throw new GuliException(20001, "上传视频失败");
            }

            Map<String, Object> map = new HashMap<>(2);
            map.put("videoSourceId", videoSourceId);
            map.put("videoOriginalName", title);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            throw new GuliException(20001, "上传视频失败");
        }
    }

    @Override
    public void deleteVideo(String videoId) {
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient();

            DeleteVideoRequest request = new DeleteVideoRequest();

            request.setVideoIds(videoId);
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }
    }

    @Override
    public String getVideoPlayAuth(String videoId) {
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient();
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            request.setVideoId(videoId);
            request.setAuthInfoTimeout(100L);

            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            return response.getPlayAuth();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001, "获取视频播放凭证失败");
        }
    }
}
