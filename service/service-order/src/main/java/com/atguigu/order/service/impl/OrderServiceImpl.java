package com.atguigu.order.service.impl;

import com.atguigu.base.handler.GuliException;
import com.atguigu.common.vo.CourseInfoVo;
import com.atguigu.common.vo.MemberForOrder;
import com.atguigu.order.client.EduClient;
import com.atguigu.order.client.UCenterClient;
import com.atguigu.order.entity.Order;
import com.atguigu.order.mapper.OrderMapper;
import com.atguigu.order.service.OrderService;
import com.atguigu.order.util.OrderNoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author HaiYu
 * @since 2022-03-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private EduClient eduClient;

    @Autowired
    private UCenterClient uCenterClient;

    @Override
    public String createOrder(String courseId, String memberId) {
        // 跨模块获取课程和用户信息
        CourseInfoVo courseInfoVo = eduClient.getCourseInfoForOrder(courseId);
        MemberForOrder member = uCenterClient.getMemberByMemberIdForOrder(memberId);
        if (courseInfoVo == null || member == null) {
            throw new GuliException(20001, "获取课程信息或用户信息失败");
        }

        // 封装数据存入数据库
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoVo.getTitle());
        order.setCourseCover(courseInfoVo.getCover());
        order.setTeacherName(courseInfoVo.getTeacherName());
        order.setTotalFee(courseInfoVo.getPrice());
        order.setMemberId(memberId);
        order.setMobile(member.getMobile());
        order.setNickname(member.getNickname());
        order.setStatus(0);
        order.setPayType(1);

        this.save(order);

        return order.getOrderNo();
    }
}
