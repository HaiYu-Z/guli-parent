package com.atguigu.sms.service.impl;

import com.atguigu.base.entity.Result;
import com.atguigu.base.handler.GuliException;
import com.atguigu.sms.client.MemberClient;
import com.atguigu.sms.service.SmsService;
import com.atguigu.sms.utli.RandomUtil;
import com.atguigu.sms.utli.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 
 * @author HaiYu
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    MemberClient memberClient;

    @Override
    public void sendCode(String phone) {
        // 向数据库查询是否重复注册
        Result result = memberClient.queryMemberByPhone(phone);
        if (!result.getSuccess()) {
            throw new GuliException(20001, "当前手机号已注册");
        }

        // 拿手机号到redis查询验证码
        String code = redisTemplate.opsForValue().get(phone);
        if (code != null && !"".equals(code)) {
            return;
        }

        // 验证码不存在，生成验证码
        code = RandomUtil.getFourBitRandom();

        // 发送验证码
        try {
            SmsUtils.sendCode(phone, code);
        } catch (Exception e) {
            // e.printStackTrace();
            throw new GuliException(20001, "发送验证码失败");
        }

        // 验证码存入redis
        redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
    }
}
