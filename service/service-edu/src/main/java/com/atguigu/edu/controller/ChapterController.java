package com.atguigu.edu.controller;


import com.atguigu.base.entity.Result;
import com.atguigu.edu.entity.Chapter;
import com.atguigu.edu.entity.vo.ChapterVo;
import com.atguigu.edu.service.ChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author HaiYu
 * @since 2022-02-23
 */
@Api(description = "章节管理")
@RestController
@RequestMapping("/edu/chapter")
public class ChapterController {
    @Autowired
    ChapterService chapterService;

    @ApiOperation("根据课程id查询章节、小节信息")
    @GetMapping("/getChapterVideoById/{courseId}")
    public Result getChapterVideoById(@PathVariable String courseId) {
        List<ChapterVo> chapterVoList = chapterService.getChapterVideoById(courseId);
        return Result.ok().data("list", chapterVoList);
    }

    @ApiOperation("根据id查询章节")
    @GetMapping("/getById/{id}")
    public Result getChapterById(@PathVariable String id) {
        Chapter chapter = chapterService.getById(id);
        return chapter != null ? Result.ok().data("chapter", chapter) : Result.error();
    }

    @ApiOperation("添加章节")
    @PostMapping("/add")
    public Result addChapter(@RequestBody Chapter chapter) {
        boolean save = chapterService.save(chapter);
        return save ? Result.ok() : Result.error();
    }

    @ApiOperation("修改章节")
    @PostMapping("/update")
    public Result updateChapter(@RequestBody Chapter chapter) {
        boolean update = chapterService.updateById(chapter);
        return update ? Result.ok() : Result.error();
    }

    @ApiOperation("根据id删除章节")
    @DeleteMapping("/delete/{id}")
    public Result deleteChapter(@PathVariable String id) {
        boolean remove = chapterService.deleteChapterInfo(id);
        return remove ? Result.ok() : Result.error();
    }
}

