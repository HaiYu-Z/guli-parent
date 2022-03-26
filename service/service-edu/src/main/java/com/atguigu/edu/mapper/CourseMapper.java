package com.atguigu.edu.mapper;

import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.vo.CoursePublishVo;
import com.atguigu.common.vo.CourseInfoVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author HaiYu
 * @since 2022-02-22
 */
@Repository
public interface CourseMapper extends BaseMapper<Course> {

    CoursePublishVo getCoursePublishById(@Param("id") String id);

    CourseInfoVo getCourseWebVo(@Param("id") String courseId);
}
