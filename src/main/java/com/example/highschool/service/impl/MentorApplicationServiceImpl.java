package com.example.highschool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.highschool.entity.MentorApplication;
import com.example.highschool.mapper.MentorApplicationMapper;
import com.example.highschool.service.MentorApplicationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 导师申请服务实现类
 */
@Service
public class MentorApplicationServiceImpl extends ServiceImpl<MentorApplicationMapper, MentorApplication> implements MentorApplicationService {

    @Override
    public MentorApplication applyMentor(MentorApplication application) {
        // 设置申请时间和初始状态
        LocalDateTime now = LocalDateTime.now();
        application.setAppliedAt(now);
        application.setCreateTime(now);
        application.setUpdateTime(now);
        application.setStatus(0); // 0-待审核
        
        // 保存申请
        save(application);
        return application;
    }

    @Override
    public boolean reviewApplication(Long id, Long teacherId, Integer status) {
        // 获取申请信息
        MentorApplication application = getById(id);
        if (application == null) {
            return false;
        }
        
        // 验证申请是否属于当前教师
        if (!application.getTeacherId().equals(teacherId)) {
            return false;
        }
        
        // 更新申请状态和时间
        application.setStatus(status);
        application.setUpdateTime(LocalDateTime.now());
        
        return updateById(application);
    }

    @Override
    public List<MentorApplication> getStudentApplications(Long studentId) {
        LambdaQueryWrapper<MentorApplication> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MentorApplication::getStudentId, studentId);
        queryWrapper.orderByDesc(MentorApplication::getAppliedAt);
        return list(queryWrapper);
    }

    @Override
    public List<MentorApplication> getTeacherApplications(Long teacherId) {
        LambdaQueryWrapper<MentorApplication> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MentorApplication::getTeacherId, teacherId);
        queryWrapper.orderByDesc(MentorApplication::getAppliedAt);
        return list(queryWrapper);
    }
}