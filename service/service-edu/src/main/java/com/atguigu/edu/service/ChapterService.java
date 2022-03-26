package com.atguigu.edu.service;

import com.atguigu.edu.entity.Chapter;
import com.atguigu.edu.entity.vo.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author HaiYu
 * @since 2022-02-23
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getChapterVideoById(String courseId);

    boolean deleteChapterInfo(String id);
}
