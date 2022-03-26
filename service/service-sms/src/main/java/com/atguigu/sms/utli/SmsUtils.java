package com.atguigu.sms.utli;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.base.handler.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SmsUtils {

    public static void sendCode(String phoneNumbers,String code) throws Exception {
        String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";
        String method = "POST";
        String appcode = "b208260049174a0fa6cfbb9f33591d9d";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phoneNumbers);
        querys.put("param", "**code**:" + code +",**minute**:5");
        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        querys.put("templateId", "908e94ccf08b4476ba6c876d13f084ad");
        Map<String, String> bodys = new HashMap<String, String>();

        /**
         * 重要提示如下:
         * HttpUtils请从
         * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
         * 下载
         *
         * 相应的依赖请参照
         * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
         */
        HttpResponse response = null;
        try {
            response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
        } finally {
            if (response != null) {
                SendSmsResponseState state = JSONObject.parseObject(EntityUtils.toString(response.getEntity()), SendSmsResponseState.class);
                if (!"0".equals(state.getCode())) {
                    log.error(state.toString());
                    throw new GuliException(20001, "发送验证码失败");
                }
            }
        }

        // System.out.println(response.toString());
        // 获取response的body
        // System.out.println(EntityUtils.toString(response.getEntity()));
    }
}
