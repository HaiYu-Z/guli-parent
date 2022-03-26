package com.atguigu.sms.utli;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @author HaiYu
 */
@Data
public class SendSmsResponseState implements Serializable {
    private String msg;
    private String code;
}
