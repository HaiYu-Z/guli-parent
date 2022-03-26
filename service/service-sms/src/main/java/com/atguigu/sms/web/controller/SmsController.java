package com.atguigu.sms.web.controller;

import com.atguigu.base.entity.Result;
import com.atguigu.sms.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * @author HaiYu
 */
@Api(description = "短信发送")
@RestController
@RequestMapping("/sms")
public class SmsController {
    @Autowired
    SmsService smsService;

    @ApiOperation("短信发送")
    @GetMapping("/sendCode/{phone}")
    public Result sendCode(@PathVariable String phone) {
        smsService.sendCode(phone);
        return Result.ok();
    }
}
