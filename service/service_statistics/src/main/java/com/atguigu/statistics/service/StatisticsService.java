package com.atguigu.statistics.service;

import com.atguigu.statistics.entity.Statistics;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author HaiYu
 * @since 2022-03-08
 */
public interface StatisticsService extends IService<Statistics> {

    void createStaDaily(String day);

    Map<String, Object> getStaDaily(Map<String, String> paramMap);
}
