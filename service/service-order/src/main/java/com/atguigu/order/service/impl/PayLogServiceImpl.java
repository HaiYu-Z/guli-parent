package com.atguigu.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.base.handler.GuliException;
import com.atguigu.order.entity.Order;
import com.atguigu.order.entity.PayLog;
import com.atguigu.order.mapper.PayLogMapper;
import com.atguigu.order.service.OrderService;
import com.atguigu.order.service.PayLogService;
import com.atguigu.order.util.HttpClient;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author HaiYu
 * @since 2022-03-06
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {
    @Autowired
    private OrderService orderService;

    // 根据订单编号生产支付二维码
    @Override
    public Map<String, Object> createNative(String orderNo) {
        try {
            // 查询订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no", orderNo);
            Order order = orderService.getOne(wrapper);

            if (order == null) {
                throw new GuliException(20001, "订单失效");
            }

            // 封装支付参数
            Map m = new HashMap();
            m.put("appid", "wx74862e0dfcf69954");// 应用id
            m.put("mch_id", "1558950191");// 商户号
            m.put("nonce_str", WXPayUtil.generateNonceStr());// 随机字符串
            m.put("body", order.getCourseTitle());// 课程名称
            m.put("out_trade_no", orderNo);// 订单编号
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");// 订单金额
            m.put("spbill_create_ip", "127.0.0.1");// 终端ip
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");// 回调地址
            m.put("trade_type", "NATIVE");// 交易类型

            // HTTPClient来根据URL访问第三方接口并且传递参数
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");

            //client设置参数，将map转化成xml
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //4、封装返回结果集

            Map map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));
            map.put("code_url", resultMap.get("code_url"));

            //微信支付二维码2小时过期，可采取2小时未支付取消订单
            //redisTemplate.opsForValue().set(orderNo, map, 120, TimeUnit.MINUTES);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "交易失败");
        }
    }

    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map resultMap = WXPayUtil.xmlToMap(xml);

            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 支付成功后，更新订单状态，记录支付日志
    @Override
    public void updateOrderStatus(Map<String, String> map) {
        // 查询订单信息
        String orderNo = map.get("out_trade_no");
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);

        // 更改状态
        if(order.getStatus() == 1) {
            return;
        }
        order.setStatus(1);
        orderService.updateById(order);

        //记录支付日志
        PayLog payLog=new PayLog();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));//微信订单号
        payLog.setAttr(JSONObject.toJSONString(map));
        baseMapper.insert(payLog);//插入到支付日志表

    }
}
