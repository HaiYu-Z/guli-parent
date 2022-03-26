package com.atguigu.sms.client;

import com.atguigu.base.entity.Result;
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
public interface MemberClient {

    // 查询手机号是否已注册
    @GetMapping("/ucenter/member/queryMemberByPhone/{phone}")
    public Result queryMemberByPhone(@PathVariable String phone);
}
