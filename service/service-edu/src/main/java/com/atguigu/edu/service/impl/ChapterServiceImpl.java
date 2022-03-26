package com.atguigu.edu.service.impl;

import com.atguigu.edu.client.VodClient;
import com.atguigu.edu.entity.Chapter;
import com.atguigu.edu.entity.Video;
import com.atguigu.edu.entity.vo.ChapterVo;
import com.atguigu.edu.entity.vo.VideoVo;
import com.atguigu.edu.mapper.ChapterMapper;
import com.atguigu.edu.service.ChapterService;
import com.atguigu.edu.service.VideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author HaiYu
 * @since 2022-02-23
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
    @Autowired
    VodClient vodClient;
    @Autowired
    ChapterService chapterService;

    @Autowired
    private VideoService videoService;

    /**
     * 根据课程id查询章节、小节信息
     * @param courseId
     * @return java.util.List<com.atguigu.edu.entity.vo.ChapterVo>
     */
    @Override
    public List<ChapterVo> getChapterVideoById(String courseId) {
        // 根据课程id查询章节信息
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        List<Chapter> chapterList = this.list(chapterQueryWrapper);

        // 根据课程id查询小节信息
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        List<Video> videoList = videoService.list(videoQueryWrapper);

        // 遍历章节信息进行封装
        List<ChapterVo> chapterVoList = new ArrayList<>();
        for (Chapter chapter : chapterList) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            chapterVoList.add(chapterVo);

            // 遍历和此章节关联小节信息进行封装
            List<VideoVo> videoVoList = new ArrayList<>();
            for (Video video : videoList) {
                if (chapter.getId().equals(video.getChapterId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVoList.add(videoVo);
                }
                chapterVo.setChildren(videoVoList);
            }
        }
        return chapterVoList;
    }

    /**
     * 删除章节并删除小节
     * @param chapterId
     * @return boolean
     */
    @Override
    public boolean deleteChapterInfo(String chapterId) {
        // 删除视频
        videoService.deleteVideoBatch("chapter_id", chapterId);

        // 删除小节
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id", chapterId);
        videoService.remove(videoQueryWrapper);

        // 删除章节
        return this.removeById(chapterId);
    }
}
