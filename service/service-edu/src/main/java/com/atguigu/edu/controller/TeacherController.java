package com.atguigu.edu.controller;


import com.atguigu.base.entity.Result;
import com.atguigu.edu.entity.Teacher;
import com.atguigu.edu.entity.vo.TeacherQuery;
import com.atguigu.edu.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author HaiYu
 * @since 2022-02-17
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/edu/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("查询所有讲师")
    @GetMapping("/getAll")
    public Result getAll() {
        List<Teacher> list = teacherService.list(null);
        return Result.ok().data("list", list);
    }
    @ApiOperation("根据id查询讲师")
    @GetMapping("{id}")
    public Result getById(@PathVariable String id) {
        Teacher teacher = teacherService.getById(id);
        return Result.ok().data("teacher", teacher);
    }

    @ApiOperation("根据ID删除讲师")
    @DeleteMapping("{id}")
    public Result deleteById(@PathVariable String id){
        boolean remove = teacherService.removeById(id);
        if (remove) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    @ApiOperation("分页查询讲师列表")
    @PostMapping("/{current}/{limit}")
    public Result getPage(@PathVariable Long current,
                          @PathVariable Long limit,
                          @RequestBody TeacherQuery teacherQuery) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();

        // 取出查询条件
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        // 判断是否为空
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_modified", end);
        }

        Page<Teacher> page = new Page<>(current, limit);
        teacherService.page(page, wrapper);

        return Result.ok()
                .data("list", page.getRecords())
                .data("total", page.getTotal());
    }

    @ApiOperation("添加讲师")
    @PostMapping
    public Result addTeacher(@RequestBody Teacher teacher) {
        boolean save = teacherService.save(teacher);
        if (save) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    @ApiOperation("修改讲师")
    @PutMapping
    public Result update(@RequestBody Teacher teacher) {
        boolean update = teacherService.updateById(teacher);
        if (update) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }
}

