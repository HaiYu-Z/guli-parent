package com.atguigu.statistics.service.impl;

import com.atguigu.statistics.client.UCenterClient;
import com.atguigu.statistics.entity.Statistics;
import com.atguigu.statistics.mapper.StatisticsMapper;
import com.atguigu.statistics.service.StatisticsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author HaiYu
 * @since 2022-03-08
 */
@Service
public class StatisticsServiceImpl extends ServiceImpl<StatisticsMapper, Statistics> implements StatisticsService {
    @Autowired
    UCenterClient uCenterClient;

    // 生成统计数据
    @Override
    public void createStaDaily(String day) {
        // 同一天不能存在两条统计数据，先删除
        QueryWrapper<Statistics> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);

        // 统计数据
        Integer countRegister = uCenterClient.countRegister(day);
        Integer loginNum = RandomUtils.nextInt(0, 10);//TODO
        Integer videoViewNum = RandomUtils.nextInt(0, 10);//TODO
        Integer courseNum = RandomUtils.nextInt(0, 10);//TODO

        //创建统计对象
        Statistics daily = new Statistics();
        daily.setRegisterNum(countRegister);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);

        baseMapper.insert(daily);
    }

    // 查询统计数据
    @Override
    public Map<String, Object> getStaDaily(Map<String, String> paramMap) {
        String begin = paramMap.get("begin");
        String end = paramMap.get("end");

        // 查询数据
        QueryWrapper<Statistics> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("date_calculated", begin, end);
        queryWrapper.orderByAsc("date_calculated");
        List<Statistics> statisticsList = baseMapper.selectList(queryWrapper);

        // 定义返回数据
        Map<String, Object> returnMap = new HashMap<>();
        List<String> dateCalculatedList = new ArrayList<>();
        List<Integer> registerNumList = new ArrayList<>();
        List<Integer> loginNumList = new ArrayList<>();
        List<Integer> videoViewNumList = new ArrayList<>();
        List<Integer> courseNumList = new ArrayList<>();

        // 遍历查询结果
        for (Statistics statistics : statisticsList) {
            // 封装x轴数据
            dateCalculatedList.add(statistics.getDateCalculated());
            // 封装y轴数据
            registerNumList.add(statistics.getRegisterNum());
            loginNumList.add(statistics.getLoginNum());
            videoViewNumList.add(statistics.getVideoViewNum());
            courseNumList.add(statistics.getCourseNum());
        }

        returnMap.put("dateCalculatedList", dateCalculatedList);
        returnMap.put("registerNumList", registerNumList);
        returnMap.put("loginNumList", loginNumList);
        returnMap.put("videoViewNumList", videoViewNumList);
        returnMap.put("courseNumList", courseNumList);
        return returnMap;
    }
}
