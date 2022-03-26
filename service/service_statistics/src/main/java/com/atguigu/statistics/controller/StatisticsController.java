package com.atguigu.statistics.controller;


import com.atguigu.base.entity.Result;
import com.atguigu.statistics.service.StatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author HaiYu
 * @since 2022-03-08
 */
@Api(description = "统计分析管理")
@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    StatisticsService statisticsService;

    @ApiOperation("生成统计数据")
    @PostMapping("/createStaDaily/{day}")
    public Result createStaDaily(@PathVariable String day) {
        statisticsService.createStaDaily(day);
        return Result.ok();
    }

    @ApiOperation("查询统计数据")
    @PostMapping("/getStaDaily")
    public Result getStaDaily(@RequestBody Map<String, String> paramMap) {
        Map<String, Object> returnMap = statisticsService.getStaDaily(paramMap);
        return Result.ok().data(returnMap);
    }
}

