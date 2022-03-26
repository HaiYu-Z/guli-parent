package com.atguigu.edu.service;

import com.atguigu.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author HaiYu
 * @since 2022-02-17
 */
public interface TeacherService extends IService<Teacher> {

    Map<String, Object> getWebPage(Long current, Long limit);
}
