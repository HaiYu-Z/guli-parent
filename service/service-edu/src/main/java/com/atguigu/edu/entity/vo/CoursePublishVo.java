package com.atguigu.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @author HaiYu
 */
@Data
public class CoursePublishVo implements Serializable {
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}
