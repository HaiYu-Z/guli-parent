package com.atguigu.edu.service.impl;

import com.atguigu.base.handler.GuliException;
import com.atguigu.common.vo.CourseInfoVo;
import com.atguigu.edu.entity.Chapter;
import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.CourseDescription;
import com.atguigu.edu.entity.Video;
import com.atguigu.edu.entity.vo.CoursePublishVo;
import com.atguigu.edu.entity.vo.CourseQueryVo;
import com.atguigu.edu.entity.vo.CourseVo;
import com.atguigu.edu.mapper.CourseMapper;
import com.atguigu.edu.service.ChapterService;
import com.atguigu.edu.service.CourseDescriptionService;
import com.atguigu.edu.service.CourseService;
import com.atguigu.edu.service.VideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author HaiYu
 * @since 2022-02-22
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    CourseDescriptionService courseDescriptionService;
    @Autowired
    ChapterService chapterService;
    @Autowired
    VideoService videoService;

    // 添加课程信息
    @Override
    public String addCourseInfo(CourseVo courseVo) {
        // 添加课程信息
        Course course = new Course();
        BeanUtils.copyProperties(courseVo, course);

        boolean save = this.save(course);
        if (!save) {
            throw new GuliException(20001, "创建课程失败");
        }
        // 添加课程描述信息
        String id = course.getId();
        String description = courseVo.getDescription();
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(id);
        courseDescription.setDescription(description);

        courseDescriptionService.save(courseDescription);
        return id;
    }

    // 获取课程信息
    @Override
    public CourseVo getCourseInfo(String courseId) {
        Course course = this.getById(courseId);
        CourseDescription courseDescription = courseDescriptionService.getById(courseId);

        CourseVo courseVo = new CourseVo();
        BeanUtils.copyProperties(course, courseVo);
        courseVo.setDescription(courseDescription.getDescription());
        return courseVo;
    }

    // 修改课程信息
    @Override
    public boolean updateCourseInfo(CourseVo courseVo) {
        Course course = new Course();
        CourseDescription courseDescription = new CourseDescription();

        BeanUtils.copyProperties(courseVo, course);
        BeanUtils.copyProperties(courseVo, courseDescription);

        boolean update = this.updateById(course);
        boolean update1 = courseDescriptionService.updateById(courseDescription);
        return update && update1;
    }

    // 根据课程id查询课程发布信息
    @Override
    public CoursePublishVo getCoursePublishById(String id) {
        return courseMapper.getCoursePublishById(id);
    }

    // 根据id删除课程相关信息
    @Override
    public void deleteCourseInfo(String courseId) {
        // 删除视频
        videoService.deleteVideoBatch("course_id", courseId);

        // 删除小节
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        videoService.remove(videoQueryWrapper);
        // 删除章节
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        chapterService.remove(chapterQueryWrapper);
        // 删除课程描述
        courseDescriptionService.removeById(courseId);
        // 删除课程
        boolean remove = this.removeById(courseId);
        if (!remove) {
            throw new GuliException(20001, "删除课程失败");
        }
    }

    @Override
    public Map<String, Object> getWebPage(Long current, Long limit, CourseQueryVo courseQueryVo) {
        String subjectParentId = courseQueryVo.getSubjectParentId();
        String subjectId = courseQueryVo.getSubjectId();
        String buyCountSort = courseQueryVo.getBuyCountSort();
        String gmtCreateSort = courseQueryVo.getGmtCreateSort();
        String priceSort = courseQueryVo.getPriceSort();

        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id" ,subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id" ,subjectId);
        }
        if (!StringUtils.isEmpty(buyCountSort)) {
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(gmtCreateSort)) {
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(priceSort)) {
            wrapper.orderByDesc("price");
        }
        wrapper.orderByDesc("buy_count");

        Page<Course> page = new Page<>(current, limit);
        this.page(page, wrapper);

        List<Course> courseList = page.getRecords();
        current = page.getCurrent();
        long pages = page.getPages();
        long size = page.getSize();
        long total = page.getTotal();
        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("courseList", courseList);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public Map<String, Object> getPage(Long current, Long limit, CourseQueryVo courseQueryVo) {
        String teacherId = courseQueryVo.getTeacherId();
        String title = courseQueryVo.getTitle();
        String subjectParentId = courseQueryVo.getSubjectParentId();
        String subjectId = courseQueryVo.getSubjectId();

        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(teacherId)) {
            wrapper.eq("teacher_id", teacherId);
        }
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.like("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.like("subject_id", subjectId);
        }

        Page<Course> page = new Page<>(current, limit);
        this.page(page, wrapper);

        List<Course> list = page.getRecords();
        long total = page.getTotal();

        Map<String, Object> map = new HashMap<>(2);
        map.put("list", list);
        map.put("total", total);
        return map;
    }

    @Override
    public CourseInfoVo getCourseWebVo(String courseId) {
        return baseMapper.getCourseWebVo(courseId);
    }
}
