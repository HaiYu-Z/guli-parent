package com.atguigu.statistics.scheduling;

import com.atguigu.statistics.service.StatisticsService;
import com.atguigu.statistics.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 
 * @author HaiYu
 */
@Slf4j
@Component
public class SchedulingTask {
    @Autowired
    private StatisticsService statisticsService;

    // 每天凌晨1点生成昨天的统计数据
    @Scheduled(cron = "0 10 1 * * ?")
    public void createStaDaily() {
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        statisticsService.createStaDaily(day);
        log.info("统计数据生成成功！日期：" + day);
    }
}
