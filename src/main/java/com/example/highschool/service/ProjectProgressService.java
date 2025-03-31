package com.example.highschool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.highschool.entity.ProjectProgress;

import java.util.List;

/**
 * 项目进度服务接口
 */
public interface ProjectProgressService extends IService<ProjectProgress> {

    /**
     * 创建项目进度
     *
     * @param progress 进度信息
     * @return 创建的进度
     */
    ProjectProgress createProgress(ProjectProgress progress);

    /**
     * 更新项目进度
     *
     * @param progress 进度信息
     * @return 更新后的进度
     */
    ProjectProgress updateProgress(ProjectProgress progress);

    /**
     * 删除项目进度
     *
     * @param id 进度ID
     * @return 是否删除成功
     */
    boolean deleteProgress(Long id);

    /**
     * 根据ID获取项目进度
     *
     * @param id 进度ID
     * @return 进度信息
     */
    ProjectProgress getProgressById(Long id);

    /**
     * 获取项目的所有进度
     *
     * @param projectId 项目ID
     * @return 进度列表
     */
    List<ProjectProgress> getProjectProgresses(Long projectId);

    /**
     * 教师提交进度反馈
     *
     * @param id 进度ID
     * @param teacherId 教师ID
     * @param feedback 反馈内容
     * @return 是否成功
     */
    boolean submitFeedback(Long id, Long teacherId, String feedback);
    
    /**
     * 学生提交项目进度
     *
     * @param progress 进度信息
     * @return 保存后的进度
     */
    ProjectProgress submitProgress(ProjectProgress progress);
}