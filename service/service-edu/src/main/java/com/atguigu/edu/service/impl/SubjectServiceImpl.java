package com.atguigu.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.base.handler.GuliException;
import com.atguigu.edu.entity.Subject;
import com.atguigu.edu.entity.vo.ExcelSubjectData;
import com.atguigu.edu.entity.vo.SubjectTree;
import com.atguigu.edu.listener.SubjectExcelListener;
import com.atguigu.edu.mapper.SubjectMapper;
import com.atguigu.edu.service.SubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author HaiYu
 * @since 2022-02-21
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public void addSubject(MultipartFile file, SubjectService subjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, ExcelSubjectData.class, new SubjectExcelListener(subjectService))
                    .sheet().doRead();

        } catch (IOException e) {
            e.printStackTrace();
            throw new GuliException(20001,"导入课程分类失败");
        }
    }

    @Override
    public List<SubjectTree> getAllAndSort() {

        // 查询一级分类
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", "0");
        List<Subject> oneSubjectList = baseMapper.selectList(wrapper);

        // 查询二级分类
        wrapper = new QueryWrapper<>();
        wrapper.ne("parent_id", "0");
        List<Subject> twoSubjectList = baseMapper.selectList(wrapper);

        // 返回的集合
        List<SubjectTree> subjectTreeList = new ArrayList<>();

        for (Subject oneSubject : oneSubjectList) {
            // 封装一级分类
            SubjectTree oneSubjectTree = new SubjectTree();
            BeanUtils.copyProperties(oneSubject, oneSubjectTree);

            // 封装二级分类
            for (Subject twoSubject : twoSubjectList) {
                if (twoSubject.getParentId().equals(oneSubject.getId())) {
                    SubjectTree twoSubjectTree = new SubjectTree();
                    BeanUtils.copyProperties(twoSubject, twoSubjectTree);

                    oneSubjectTree.getChildren().add(twoSubjectTree);
                }
            }
            subjectTreeList.add(oneSubjectTree);
        }
        return subjectTreeList;
    }
}
