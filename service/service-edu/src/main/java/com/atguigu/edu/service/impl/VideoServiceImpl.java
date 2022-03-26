package com.atguigu.edu.service.impl;

import com.atguigu.edu.client.VodClient;
import com.atguigu.edu.entity.Video;
import com.atguigu.edu.mapper.VideoMapper;
import com.atguigu.edu.service.VideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author HaiYu
 * @since 2022-02-23
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
    @Autowired
    VodClient vodClient;

    /**
     * 批量删除阿里云视频
     * @param id
     * @param databaseField 在那个字段中查找id值
     * @return
     */
    @Override
    public void deleteVideoBatch(String databaseField, String id) {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq(databaseField, id);

        List<Video> videoList = this.list(wrapper);
        List<String> videoSourceIdList = new ArrayList<>();
        for (Video video : videoList) {
            String videoSourceId = video.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                videoSourceIdList.add(videoSourceId);
            }
        }

        vodClient.deleteVideo(org.apache.commons.lang.StringUtils.join(videoSourceIdList, ","));
    }
}
