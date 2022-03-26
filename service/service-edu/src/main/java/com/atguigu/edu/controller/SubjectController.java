package com.atguigu.edu.controller;


import com.atguigu.base.entity.Result;
import com.atguigu.edu.entity.vo.SubjectTree;
import com.atguigu.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author HaiYu
 * @since 2022-02-21
 */
@Api(description = "课程分类管理")
@RestController
@RequestMapping("/edu/subject")
public class SubjectController {
    @Autowired
    SubjectService subjectService;

    @ApiOperation("批量导入课程分类")
    @PostMapping("/addSubject")
    public Result addSubject(MultipartFile file) {
        subjectService.addSubject(file, subjectService);
        return Result.ok();
    }

    @ApiOperation("获取全部课程并分类")
    @GetMapping("/getAllAndSort")
    public Result getAllAndSort() {
        List<SubjectTree> list = subjectService.getAllAndSort();
        return Result.ok().data("list", list);
    }
}

