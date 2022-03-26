package com.atguigu.order.client;

import com.atguigu.common.vo.MemberForOrder;
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

   // 根据memberId获取用户信息
    @GetMapping("/ucenter/member/getMemberByMemberIdForOrder/{memberId}")
    MemberForOrder getMemberByMemberIdForOrder(@PathVariable String memberId);
}
