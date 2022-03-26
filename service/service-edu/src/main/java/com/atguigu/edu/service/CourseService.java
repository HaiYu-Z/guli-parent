package com.atguigu.edu.service;

import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.vo.CourseVo;
import com.atguigu.edu.entity.vo.CoursePublishVo;
import com.atguigu.edu.entity.vo.CourseQueryVo;
import com.atguigu.common.vo.CourseInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author HaiYu
 * @since 2022-02-22
 */
public interface CourseService extends IService<Course> {

    String addCourseInfo(CourseVo courseVo);

    CourseVo getCourseInfo(String courseId);

    boolean updateCourseInfo(CourseVo courseVo);

    CoursePublishVo getCoursePublishById(String id);

    void deleteCourseInfo(String courseId);

    Map<String, Object> getWebPage(Long current, Long limit, CourseQueryVo courseQueryVo);

    Map<String, Object> getPage(Long current, Long limit, CourseQueryVo courseQueryVo);

    CourseInfoVo getCourseWebVo(String courseId);
}
