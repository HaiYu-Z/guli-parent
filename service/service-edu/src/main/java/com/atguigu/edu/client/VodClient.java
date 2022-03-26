package com.atguigu.edu.client;

import com.atguigu.base.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 
 * @author HaiYu
 */
@Component
@FeignClient(value = "service-vod", fallback = VodFileDegradeFeignClient.class)
public interface VodClient {
    // 删除视频
    @DeleteMapping("/vod/video/deleteVideo/{videoId}")
    public Result deleteVideo(@PathVariable String videoId);
}
