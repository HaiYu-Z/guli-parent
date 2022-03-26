package com.atguigu.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.base.handler.GuliException;
import com.atguigu.edu.entity.Subject;
import com.atguigu.edu.entity.vo.ExcelSubjectData;
import com.atguigu.edu.service.SubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 
 * @author HaiYu
 */
public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    public SubjectService subjectService;

    public SubjectExcelListener() {}
    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        // 读取数据判空
        if (excelSubjectData == null) {
            throw new GuliException(20001,"导入课程分类失败");
        }

        // 判断一级分类是否重复
        Subject existOneSubject = existOneSubject(subjectService, excelSubjectData.getOneSubjectName());

        // 一级不重复插入数据库
        if (existOneSubject == null) {
            existOneSubject = new Subject();
            existOneSubject.setTitle(excelSubjectData.getOneSubjectName());
            existOneSubject.setParentId("0");
            subjectService.save(existOneSubject);
        }

        // 判断二级分类是否重复
        String pid = existOneSubject.getId();
        Subject existTowSubject = existTowSubject(subjectService, excelSubjectData.getTwoSubjectName(), pid);

        // 二级不重复插入数据库
        if (existTowSubject == null) {
            existTowSubject = new Subject();
            existTowSubject.setTitle(excelSubjectData.getTwoSubjectName());
            existTowSubject.setParentId(pid);
            subjectService.save(existTowSubject);
        }

    }

    // 判断一级分类是否重复
    private Subject existOneSubject(SubjectService subjectService, String name) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", "0");
        wrapper.eq("title", name);
        return subjectService.getOne(wrapper);
    }

    // 判断二级分类是否重复
    private Subject existTowSubject(SubjectService subjectService, String name, String pid) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", pid);
        wrapper.eq("title", name);
        return subjectService.getOne(wrapper);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
