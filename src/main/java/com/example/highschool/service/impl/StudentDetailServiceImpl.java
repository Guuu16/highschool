package com.example.highschool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.highschool.entity.StudentDetail;
import com.example.highschool.mapper.StudentDetailMapper;
import com.example.highschool.service.StudentDetailService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 学生详细信息实现类
 */
@Service
public class StudentDetailServiceImpl extends ServiceImpl<StudentDetailMapper, StudentDetail> implements StudentDetailService {

    @Override
    public StudentDetail getByStudentId(Long studentId) {
        QueryWrapper<StudentDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id", studentId);
        return getOne(wrapper);
    }

    @Override
    public boolean updateStudentDetail(StudentDetail studentDetail) {
        studentDetail.setUpdateTime(LocalDateTime.now());
        return updateById(studentDetail);
    }
}
