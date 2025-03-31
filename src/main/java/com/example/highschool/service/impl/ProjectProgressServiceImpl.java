package com.example.highschool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.highschool.entity.ProjectProgress;
import com.example.highschool.mapper.ProjectProgressMapper;
import com.example.highschool.service.ProjectProgressService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 项目进度服务实现类
 */
@Service
public class ProjectProgressServiceImpl extends ServiceImpl<ProjectProgressMapper, ProjectProgress> implements ProjectProgressService {

    @Override
    public ProjectProgress createProgress(ProjectProgress progress) {
        // 设置提交时间
        LocalDateTime now = LocalDateTime.now();
        progress.setSubmittedAt(now);
        
        // 保存进度
        save(progress);
        return progress;
    }

    @Override
    public ProjectProgress updateProgress(ProjectProgress progress) {
        // 设置提交时间
        progress.setSubmittedAt(LocalDateTime.now());
        
        // 更新进度
        updateById(progress);
        return progress;
    }

    @Override
    public boolean deleteProgress(Long id) {
        return removeById(id);
    }

    @Override
    public ProjectProgress getProgressById(Long id) {
        return getById(id);
    }

    @Override
    public List<ProjectProgress> getProjectProgresses(Long projectId) {
        LambdaQueryWrapper<ProjectProgress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProjectProgress::getProjectId, projectId);
        queryWrapper.orderByDesc(ProjectProgress::getSubmittedAt);
        return list(queryWrapper);
    }

    @Override
    public boolean submitFeedback(Long id, Long teacherId, String feedback) {
        // 获取进度信息
        ProjectProgress progress = getById(id);
        if (progress == null) {
            return false;
        }
        
        // 更新反馈和时间
        progress.setFeedback(feedback);
        progress.setSubmittedAt(LocalDateTime.now());
        
        return updateById(progress);
    }
    
    @Override
    public ProjectProgress submitProgress(ProjectProgress progress) {
        // 设置提交时间
        LocalDateTime now = LocalDateTime.now();
        progress.setSubmittedAt(now);
        
        // 保存进度
        save(progress);
        return progress;
    }
}