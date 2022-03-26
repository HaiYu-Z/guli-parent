package com.atguigu.order.controller;


import com.atguigu.base.entity.Result;
import com.atguigu.common.utils.JwtUtils;
import com.atguigu.order.entity.Order;
import com.atguigu.order.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author HaiYu
 * @since 2022-03-06
 */
@Api(description = "前台订单管理")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("根据课程id、用户id创建订单")
    @PostMapping("/createOrder/{courseId}")
    public Result createOrder(@PathVariable String courseId,
                              HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        String orderNo = orderService.createOrder(courseId, memberId);
        return Result.ok().data("orderNo", orderNo);
    }

    @ApiOperation("根据订单编号查询订单信息")
    @GetMapping("/getOrderInfo/{orderNo}")
    public Result getOrderInfo(@PathVariable String orderNo) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);
        return order != null ?
                Result.ok().data("order", order) :
                Result.error().message("没有这个订单");
    }

    @ApiOperation("根据课程id、用户id查询该课程是否已购买，给前台CourseController调用")
    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,
                               @PathVariable String memberId) {
        if (StringUtils.isEmpty(memberId)) {
            return false;
        }

        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", 1);
        int count = orderService.count(wrapper);

        return count > 0;
    }
}

