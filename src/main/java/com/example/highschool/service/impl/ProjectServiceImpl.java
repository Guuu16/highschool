package com.example.highschool.service.impl;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import com.example.highschool.dto.ProjectDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.highschool.entity.Project;
import com.example.highschool.mapper.ProjectMapper;
import com.example.highschool.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 项目服务实现类
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Override
    public String uploadPlanFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get("uploads");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return "/api/uploads/" + fileName;
    }

    @Override
    public Project createProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setStudentId(projectDTO.getStudentId());
        project.setTeacherId(projectDTO.getTeacherId());
        project.setTitle(projectDTO.getTitle());
        project.setDescription(projectDTO.getDescription());
        project.setCategoryId(projectDTO.getCategoryId());
        project.setStatus(projectDTO.getStatus());
        project.setCredit(projectDTO.getCredit());
        project.setPlanFileUrl(projectDTO.getPlanFileUrl());
        
        // 设置创建时间和更新时间
        LocalDateTime now = LocalDateTime.now();
        project.setCreateTime(now);
        project.setUpdateTime(now);
        // 设置初始状态
        project.setStatus(0); // 0-申请中
        
        // 保存项目
        save(project);
        return project;
    }

    @Override
    public Project updateProject(Project project) {
        // 设置更新时间
        project.setUpdateTime(LocalDateTime.now());
        
        // 更新项目
        updateById(project);
        return project;
    }

    @Override
    public Project updateProject(ProjectDTO projectDTO) {
        Project project = new Project();
        // 复制DTO到实体
        project.setId(projectDTO.getId());
        project.setStudentId(projectDTO.getStudentId());
        project.setTeacherId(projectDTO.getTeacherId());
        project.setCategoryId(projectDTO.getCategoryId());
        project.setTitle(projectDTO.getTitle());
        project.setDescription(projectDTO.getDescription());
        project.setStatus(projectDTO.getStatus());
        project.setCredit(projectDTO.getCredit());
        project.setPlanFileUrl(projectDTO.getPlanFileUrl());
        project.setUpdateTime(LocalDateTime.now());
        
        updateById(project);
        return project;
    }

    @Override
    public boolean deleteProject(Long id) {
        return removeById(id);
    }

    @Override
    public Project getProjectById(Long id) {
        return getById(id);
    }

    @Override
    public IPage<Project> listProjects(Page<Project> page, String title, Integer status) {
        LambdaQueryWrapper<Project> queryWrapper = new LambdaQueryWrapper<>();
        
        // 添加标题查询条件
        if (StringUtils.hasText(title)) {
            queryWrapper.like(Project::getTitle, title);
        }
        
        // 添加状态查询条件
        if (status != null) {
            queryWrapper.eq(Project::getStatus, status);
        }
        
        // 按创建时间降序排序
        queryWrapper.orderByDesc(Project::getCreateTime);
        
        return page(page, queryWrapper);
    }

    @Override
    public List<Project> getStudentProjects(Long studentId) {
        LambdaQueryWrapper<Project> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Project::getStudentId, studentId);
        queryWrapper.orderByDesc(Project::getCreateTime);
        return list(queryWrapper);
    }

    @Override
    public List<Project> getTeacherProjects(Long teacherId) {
        LambdaQueryWrapper<Project> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Project::getTeacherId, teacherId);
        queryWrapper.orderByDesc(Project::getCreateTime);
        return list(queryWrapper);
    }

    @Override
    public boolean reviewProject(Long id, Long teacherId, Integer status, String feedback, Integer credit) {
        // 获取项目信息
        Project project = getById(id);
        if (project == null) {
            return false;
        }
        
        // 验证项目是否属于当前教师
        if (!project.getTeacherId().equals(teacherId)) {
            return false;
        }
        
        // 更新项目状态、反馈和时间
        project.setStatus(status);
        project.setFeedback(feedback);
        project.setUpdateTime(LocalDateTime.now());
        
        // 如果审核通过且设置了学分，则更新学分
        if (status == 1 && credit != null) {
            project.setCredit(credit);
        }
        
        return updateById(project);
    }
}
