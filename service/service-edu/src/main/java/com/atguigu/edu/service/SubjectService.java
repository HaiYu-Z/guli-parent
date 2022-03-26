package com.atguigu.edu.service;

import com.atguigu.edu.entity.Subject;
import com.atguigu.edu.entity.vo.SubjectTree;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author HaiYu
 * @since 2022-02-21
 */
public interface SubjectService extends IService<Subject> {

    void addSubject(MultipartFile file, SubjectService subjectService);

    List<SubjectTree> getAllAndSort();
}
