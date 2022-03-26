package com.atguigu.order.client;

import com.atguigu.common.vo.CourseInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 
 * @author HaiYu
 */
@Component
@FeignClient("service-edu")
public interface EduClient {

    // 根据课程id查询课程相关信息，给订单模块调用
    @GetMapping("/edu/web/course/getCourseInfoForOrder/{courseId}")
    public CourseInfoVo getCourseInfoForOrder(@PathVariable String courseId);
}
