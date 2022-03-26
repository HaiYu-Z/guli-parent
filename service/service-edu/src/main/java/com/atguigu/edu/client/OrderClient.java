package com.atguigu.edu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 
 * @author HaiYu
 */
@Component
@FeignClient("service-order")
public interface OrderClient {

    // 根据课程id、用户id查询该课程是否已购买，给前台CourseController调用
    @GetMapping("/order/isBuyCourse/{courseId}/{memberId}")
    boolean isBuyCourse(@PathVariable String courseId,
                        @PathVariable String memberId);
}
