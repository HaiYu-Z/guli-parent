package com.atguigu.statistics.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 
 * @author HaiYu
 */
@Component
@FeignClient("service-ucenter")
public interface UCenterClient {
    // 统计注册人数，给statistics模块调用
    @GetMapping("/ucenter/member/countRegister/{day}")
    public Integer countRegister(@PathVariable String day);
}
