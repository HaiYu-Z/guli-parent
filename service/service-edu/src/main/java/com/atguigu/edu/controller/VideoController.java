package com.atguigu.edu.controller;


import com.atguigu.base.entity.Result;
import com.atguigu.edu.client.VodClient;
import com.atguigu.edu.entity.Video;
import com.atguigu.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author HaiYu
 * @since 2022-02-23
 */
@Api(description = "小节管理")
@RestController
@RequestMapping("/edu/video")
public class VideoController {
    @Autowired
    VideoService videoService;
    @Autowired
    VodClient vodClient;

    @ApiOperation("根据id查询小节")
    @GetMapping("/getById/{id}")
    public Result getVideoById(@PathVariable String id) {
        Video video = videoService.getById(id);
        return video != null ? Result.ok().data("video", video) : Result.error();
    }

    @ApiOperation("添加小节")
    @PostMapping("/add")
    public Result addVideo(@RequestBody Video video) {
        boolean save = videoService.save(video);
        return save ? Result.ok() : Result.error();
    }

    @ApiOperation("修改小节")
    @PostMapping("/update")
    public Result updateVideo(@RequestBody Video video) {
        boolean update = videoService.updateById(video);
        return update ? Result.ok() : Result.error();
    }

    @ApiOperation("根据id删除小节")
    @DeleteMapping("/delete/{id}")
    public Result deleteVideo(@PathVariable String id) {
        Video video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            vodClient.deleteVideo(videoSourceId);
        }

        boolean remove = videoService.removeById(id);
        return remove ? Result.ok() : Result.error();
    }
}

