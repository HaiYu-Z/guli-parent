package com.atguigu.edu.web.controller;

import com.atguigu.base.entity.Result;
import com.atguigu.common.utils.JwtUtils;
import com.atguigu.common.vo.CourseInfoVo;
import com.atguigu.edu.client.OrderClient;
import com.atguigu.edu.entity.vo.ChapterVo;
import com.atguigu.edu.entity.vo.CourseQueryVo;
import com.atguigu.edu.service.ChapterService;
import com.atguigu.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author HaiYu
 */
@Api(description = "前台课程管理")
@RestController
@RequestMapping("/edu/web/course")
public class CourseWebController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private OrderClient orderClient;

    @ApiOperation("前台分页查询课程列表")
    @PostMapping("/{current}/{limit}")
    public Result getWebPage(@PathVariable Long current,
                             @PathVariable Long limit,
                             @RequestBody CourseQueryVo courseQueryVo) {
        Map<String, Object> map = courseService.getWebPage(current,limit,courseQueryVo);
        return Result.ok().data(map);
    }

    @ApiOperation(value = "根据课程id查询课程相关信息")
    @GetMapping("getCourseWebInfo/{courseId}")
    public Result getCourseWebInfo(@PathVariable String courseId,
                                   HttpServletRequest request){
        //1 查询课程相关信息存入CourseWebVo
        CourseInfoVo courseInfoVo = courseService.getCourseWebVo(courseId);
        //2查询课程大纲信息
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoById(courseId);

        // 查询用户是否已购买该课程
        boolean isBuyCourse;
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (!StringUtils.isEmpty(memberId)) {
            isBuyCourse = orderClient.isBuyCourse(courseId, memberId);
        } else {
            isBuyCourse = false;
        }

        return Result.ok()
                .data("courseWebVo", courseInfoVo)
                .data("chapterVideoList",chapterVideoList)
                .data("isBuyCourse", isBuyCourse);
    }

    @ApiOperation(value = "根据课程id查询课程相关信息，给订单模块调用")
    @GetMapping("getCourseInfoForOrder/{courseId}")
    public CourseInfoVo getCourseInfoForOrder(@PathVariable String courseId){
        return courseService.getCourseWebVo(courseId);
    }
}
