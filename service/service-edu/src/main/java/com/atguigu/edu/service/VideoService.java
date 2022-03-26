package com.atguigu.edu.service;

import com.atguigu.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author HaiYu
 * @since 2022-02-23
 */
public interface VideoService extends IService<Video> {
    /**
     * 批量删除阿里云视频
     * @param id
     * @param databaseField 在那个字段中查找id
     * @return
     */
    public void deleteVideoBatch(String databaseField, String id);
}
