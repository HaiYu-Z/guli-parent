package com.atguigu.edu.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 
 * @author HaiYu
 */
@Data
public class DemoData {
    @ExcelProperty("学生编号")
    private Integer id;
    @ExcelProperty("学生姓名")
    private String name;
}
