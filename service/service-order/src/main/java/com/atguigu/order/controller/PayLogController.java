package com.atguigu.order.controller;


import com.atguigu.base.entity.Result;
import com.atguigu.order.service.PayLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author HaiYu
 * @since 2022-03-06
 */
@Api(description = "支付管理")
@RestController
@RequestMapping("/order/payLog")
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    @ApiOperation("根据订单编号生产支付二维码")
    @GetMapping("/createNative/{orderNo}")
    public Result createNative(@PathVariable String orderNo) {
        Map<String, Object> map = payLogService.createNative(orderNo);
        return Result.ok().data(map);
    }

    @ApiOperation("根据订单编号查询支付状态")
    @GetMapping("/queryPayStatus/{orderNo}")
    public Result queryPayStatus(@PathVariable String orderNo) {
        // 调用微信接口查询支付状态
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        // 判断支付状态
        if (map == null) {
            return Result.error().message("支付出错");
        }

        if ("SUCCESS".equals(map.get("trade_state"))) {
            // 支付成功后，更新订单状态，记录支付日志
            payLogService.updateOrderStatus(map);
            return Result.ok().message("支付成功");
        }

        return Result.ok().code(25000).message("支付中");
    }
}

