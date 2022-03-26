package com.atguigu.edu.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author HaiYu
 */
public class ExcelTest {
    @Test
    public void test() {
        String fileName = "D:\\1.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class).sheet("Sheet1").doWrite(data());
    }

    private static List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setId(i);
            data.setName("张三"+i);
            list.add(data);
        }
        return list;
    }


}