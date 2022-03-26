package com.atguigu.edu.client;

import com.atguigu.base.entity.Result;
import org.springframework.stereotype.Component;

/**
 * 
 * @author HaiYu
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{

    @Override
    public Result deleteVideo(String videoId) {
        return Result.error().message("删除失败");
    }
}
