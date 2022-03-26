package com.atguigu.edu.web.controller;

import com.atguigu.base.entity.Result;
import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.Teacher;
import com.atguigu.edu.service.CourseService;
import com.atguigu.edu.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 
 * @author HaiYu
 */
@Api(description = "首页显示")
@RestController
@RequestMapping("/edu/index")
public class IndexController {
    @Autowired
    CourseService courseService;
    @Autowired
    TeacherService teacherService;

    @ApiOperation("首页展示8条课程信息4条讲师信息")
    @GetMapping("/getCourseTeacherList")
    public Result getCourseTeacherList() {
        // 8条课程信息
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("status", "Normal");
        courseQueryWrapper.orderByDesc("gmt_create");
        courseQueryWrapper.last("limit 8");
        List<Course> courseList = courseService.list(courseQueryWrapper);

        // 4条讲师信息
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("gmt_create");
        teacherQueryWrapper.last("limit 4");
        List<Teacher> teacherList = teacherService.list(teacherQueryWrapper);
        return Result.ok().data("courseList", courseList).data("teacherList", teacherList);
    }
}
