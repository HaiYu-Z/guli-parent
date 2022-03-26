package com.atguigu.edu.controller;


import com.atguigu.base.entity.Result;
import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.vo.CourseVo;
import com.atguigu.edu.entity.vo.CoursePublishVo;
import com.atguigu.edu.entity.vo.CourseQueryVo;
import com.atguigu.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author HaiYu
 * @since 2022-02-22
 */
@RestController
@RequestMapping("/edu/course")
@Api(description = "课程管理")
public class CourseController {
    @Autowired
    CourseService courseService;

    @ApiOperation("分页查询课程列表")
    @PostMapping("/{current}/{limit}")
    public Result getPage(@PathVariable Long current,
                          @PathVariable Long limit,
                          @RequestBody CourseQueryVo courseQueryVo) {
        Map<String, Object> map = courseService.getPage(current, limit, courseQueryVo);
        return Result.ok().data(map);
    }

    @ApiOperation("添加课程信息")
    @PostMapping("/addCourseInfo")
    public Result addCourseInfo(@RequestBody CourseVo courseVo) {
        String courseId = courseService.addCourseInfo(courseVo);
        return Result.ok().data("courseId", courseId);
    }
    @ApiOperation("修改课程信息")
    @PostMapping("/updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseVo courseVo) {
        boolean update = courseService.updateCourseInfo(courseVo);
        return update ? Result.ok() : Result.error().message("修改课程信息失败");
    }

    @ApiOperation("获取课程信息")
    @GetMapping("/getCourseInfo/{courseId}")
    public Result getCourseInfo(@PathVariable String courseId) {
        CourseVo courseVo = courseService.getCourseInfo(courseId);
        return Result.ok().data("courseInfo", courseVo);
    }

    @ApiOperation("根据课程id查询课程发布信息")
    @GetMapping("/getCoursePublishById/{id}")
    public Result getCoursePublishById(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.getCoursePublishById(id);
        return Result.ok().data("coursePublish", coursePublishVo);
    }

    @ApiOperation("修改课程状态为发布")
    @PostMapping("/publishCourse/{id}")
    public Result publishCourse(@PathVariable String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus(Course.NORMAL);

        boolean update = courseService.updateById(course);
        return update ? Result.ok() : Result.error();
    }

    @ApiOperation("根据id删除课程相关信息")
    @DeleteMapping("/deleteCourseInfo/{courseId}")
    public Result deleteCourseInfo(@PathVariable String courseId) {
        courseService.deleteCourseInfo(courseId);
        return Result.ok();
    }
}

