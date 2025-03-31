package com.example.highschool.controller;

import com.example.highschool.common.api.Result;
import com.example.highschool.entity.Project;
import com.example.highschool.entity.ProjectProgress;
import com.example.highschool.entity.User;
import com.example.highschool.service.ProjectProgressService;
import com.example.highschool.service.ProjectService;
import com.example.highschool.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目进度控制器
 */
@RestController
@RequestMapping("/project-progress")
@Tag(name = "项目进度管理", description = "项目进度的创建、更新、查询等接口")
public class ProjectProgressController {

    @Autowired
    private ProjectProgressService projectProgressService;
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "创建项目进度", description = "学生创建项目进度")
    public Result<ProjectProgress> createProgress(@RequestBody ProjectProgress progress) {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        
        // 验证项目是否存在且属于当前学生
        Project project = projectService.getProjectById(progress.getProjectId());
        if (project == null) {
            return Result.failed("项目不存在");
        }
        
        if (!project.getStudentId().equals(currentUser.getId())) {
            return Result.forbidden("无权限为此项目创建进度");
        }
        
        // 创建项目进度
        ProjectProgress createdProgress = projectProgressService.createProgress(progress);
        return Result.success(createdProgress);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新项目进度", description = "学生更新项目进度")
    public Result<ProjectProgress> updateProgress(@PathVariable Long id, @RequestBody ProjectProgress progress) {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        
        // 获取原进度信息
        ProjectProgress existingProgress = projectProgressService.getProgressById(id);
        if (existingProgress == null) {
            return Result.failed("项目进度不存在");
        }
        
        // 验证项目是否属于当前学生
        Project project = projectService.getProjectById(existingProgress.getProjectId());
        if (project == null || !project.getStudentId().equals(currentUser.getId())) {
            return Result.forbidden("无权限修改此项目进度");
        }
        
        // 设置ID和项目ID
        progress.setId(id);
        progress.setProjectId(existingProgress.getProjectId());
        
        // 更新项目进度
        ProjectProgress updatedProgress = projectProgressService.updateProgress(progress);
        return Result.success(updatedProgress);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除项目进度", description = "学生删除项目进度")
    public Result<Boolean> deleteProgress(@PathVariable Long id) {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        
        // 获取进度信息
        ProjectProgress progress = projectProgressService.getProgressById(id);
        if (progress == null) {
            return Result.failed("项目进度不存在");
        }
        
        // 验证项目是否属于当前学生
        Project project = projectService.getProjectById(progress.getProjectId());
        if (project == null || !project.getStudentId().equals(currentUser.getId())) {
            return Result.forbidden("无权限删除此项目进度");
        }
        
        // 删除项目进度
        boolean result = projectProgressService.deleteProgress(id);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取项目进度详情", description = "获取项目进度详情")
    public Result<ProjectProgress> getProgress(@PathVariable Long id) {
        ProjectProgress progress = projectProgressService.getProgressById(id);
        if (progress == null) {
            return Result.failed("项目进度不存在");
        }
        return Result.success(progress);
    }

    @GetMapping("/project/{projectId}")
    @Operation(summary = "获取项目的所有进度", description = "获取指定项目的所有进度")
    public Result<List<ProjectProgress>> getProjectProgresses(@PathVariable Long projectId) {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        
        // 验证项目是否存在
        Project project = projectService.getProjectById(projectId);
        if (project == null) {
            return Result.failed("项目不存在");
        }
        
        // 验证权限（学生只能查看自己的项目，教师只能查看自己指导的项目）
        String role = currentUser.getRole();
        if ("STUDENT".equals(role) && !project.getStudentId().equals(currentUser.getId())) {
            return Result.forbidden("无权限查看此项目进度");
        } else if ("TEACHER".equals(role) && !project.getTeacherId().equals(currentUser.getId())) {
            return Result.forbidden("无权限查看此项目进度");
        }
        
        // 获取项目进度列表
        List<ProjectProgress> progresses = projectProgressService.getProjectProgresses(projectId);
        return Result.success(progresses);
    }

    @PutMapping("/{id}/feedback")
    @Operation(summary = "提交进度反馈", description = "教师提交项目进度反馈")
    public Result<Boolean> submitFeedback(
            @PathVariable Long id,
            @RequestParam String feedback) {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        
        // 验证是否为教师
        if (!"TEACHER".equals(currentUser.getRole()) && !"ADMIN".equals(currentUser.getRole())) {
            return Result.forbidden("只有教师可以提交反馈");
        }
        
        // 获取进度信息
        ProjectProgress progress = projectProgressService.getProgressById(id);
        if (progress == null) {
            return Result.failed("项目进度不存在");
        }
        
        // 验证项目是否由当前教师指导
        Project project = projectService.getProjectById(progress.getProjectId());
        if (project == null || !project.getTeacherId().equals(currentUser.getId())) {
            return Result.forbidden("无权限为此项目进度提交反馈");
        }
        
        // 提交反馈
        boolean result = projectProgressService.submitFeedback(id, currentUser.getId(), feedback);
        return Result.success(result);
    }
}