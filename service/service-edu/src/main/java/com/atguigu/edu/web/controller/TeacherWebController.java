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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author HaiYu
 */
@Api(description = "前台讲师展示")
@RestController
@RequestMapping("/edu/web/teacher")
public class TeacherWebController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @ApiOperation("前台分页查询讲师列表")
    @GetMapping("/{current}/{limit}")
    public Result getWebPage(@PathVariable Long current,
                          @PathVariable Long limit) {
        Map<String, Object> map = teacherService.getWebPage(current,limit);
        return Result.ok().data(map);
    }

    @ApiOperation("前台查询讲师详情")
    @GetMapping("/getTeacherInfoById/{teacherId}")
    public Result getTeacherInfoById(@PathVariable String teacherId) {
        Teacher teacher = teacherService.getById(teacherId);

        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<Course> courseList = courseService.list(wrapper);

        return Result.ok().data("teacher", teacher)
                .data("courseList", courseList);
    }
}
