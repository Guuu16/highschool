package com.example.highschool.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.highschool.entity.Project;

import java.util.List;

/**
 * 项目服务接口
 */
public interface ProjectService {

    /**
     * 创建项目
     *
     * @param project 项目信息
     * @return 创建的项目
     */
    Project createProject(Project project);

    /**
     * 更新项目
     *
     * @param project 项目信息
     * @return 更新后的项目
     */
    Project updateProject(Project project);

    /**
     * 删除项目
     *
     * @param id 项目ID
     * @return 是否删除成功
     */
    boolean deleteProject(Long id);

    /**
     * 根据ID获取项目
     *
     * @param id 项目ID
     * @return 项目信息
     */
    Project getProjectById(Long id);

    /**
     * 分页查询项目列表
     *
     * @param page 分页参数
     * @param title 项目标题（可选）
     * @param status 项目状态（可选）
     * @return 项目列表
     */
    IPage<Project> listProjects(Page<Project> page, String title, Integer status);

    /**
     * 获取学生的项目列表
     *
     * @param studentId 学生ID
     * @return 项目列表
     */
    List<Project> getStudentProjects(Long studentId);

    /**
     * 获取教师指导的项目列表
     *
     * @param teacherId 教师ID
     * @return 项目列表
     */
    List<Project> getTeacherProjects(Long teacherId);
    
    /**
     * 审核项目
     *
     * @param id 项目ID
     * @param teacherId 教师ID
     * @param status 审核状态
     * @param feedback 反馈信息（可选）
     * @return 是否审核成功
     */
    boolean reviewProject(Long id, Long teacherId, Integer status, String feedback, Integer credit);
}
