package com.example.highschool.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.highschool.common.api.Result;
import com.example.highschool.entity.Project;
import com.example.highschool.entity.User;
import com.example.highschool.service.ProjectService;
import com.example.highschool.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目控制器
 */
@RestController
@RequestMapping("/projects")
@Tag(name = "项目管理", description = "项目的创建、更新、查询等接口")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "创建项目", description = "学生创建项目")
    public Result<Project> createProject(@RequestBody Project project) {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        
        // 验证是否为学生
        if (!"STUDENT".equals(currentUser.getRole())) {
            return Result.forbidden("只有学生可以创建项目");
        }
        
        // 设置学生ID
        project.setStudentId(currentUser.getId());
        
        // 创建项目
        Project createdProject = projectService.createProject(project);
        return Result.success(createdProject);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新项目", description = "学生更新项目")
    public Result<Project> updateProject(@PathVariable Long id, @RequestBody Project project) {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        
        // 获取原项目信息
        Project existingProject = projectService.getProjectById(id);
        if (existingProject == null) {
            return Result.failed("项目不存在");
        }
        
        // 验证是否为项目创建者
        if (!existingProject.getStudentId().equals(currentUser.getId())) {
            return Result.forbidden("无权限修改此项目");
        }
        
        // 设置ID和学生ID
        project.setId(id);
        project.setStudentId(currentUser.getId());
        
        // 更新项目
        Project updatedProject = projectService.updateProject(project);
        return Result.success(updatedProject);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除项目", description = "学生删除项目")
    public Result<Boolean> deleteProject(@PathVariable Long id) {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        
        // 获取项目信息
        Project project = projectService.getProjectById(id);
        if (project == null) {
            return Result.failed("项目不存在");
        }
        
        // 验证是否为项目创建者
        if (!project.getStudentId().equals(currentUser.getId())) {
            return Result.forbidden("无权限删除此项目");
        }
        
        // 删除项目
        boolean result = projectService.deleteProject(id);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取项目详情", description = "获取项目详情")
    public Result<Project> getProject(@PathVariable Long id) {
        Project project = projectService.getProjectById(id);
        if (project == null) {
            return Result.failed("项目不存在");
        }
        return Result.success(project);
    }

    @GetMapping
    @Operation(summary = "分页查询项目", description = "分页查询项目列表")
    public Result<IPage<Project>> listProjects(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer status) {
        Page<Project> page = new Page<>(pageNum, pageSize);
        IPage<Project> projectPage = projectService.listProjects(page, title, status);
        return Result.success(projectPage);
    }

    @GetMapping("/student")
    @Operation(summary = "获取学生的项目", description = "获取当前登录学生的所有项目")
    public Result<List<Project>> getStudentProjects() {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        
        // 验证是否为学生
        if (!"STUDENT".equals(currentUser.getRole())) {
            return Result.forbidden("只有学生可以查看自己的项目");
        }
        
        // 获取学生项目
        List<Project> projects = projectService.getStudentProjects(currentUser.getId());
        return Result.success(projects);
    }

    @GetMapping("/teacher")
    @Operation(summary = "获取教师指导的项目", description = "获取当前登录教师指导的所有项目")
    public Result<List<Project>> getTeacherProjects() {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        
        // 验证是否为教师
        if (!"TEACHER".equals(currentUser.getRole()) && !"ADMIN".equals(currentUser.getRole())) {
            return Result.forbidden("只有教师可以查看指导的项目");
        }
        
        // 获取教师项目
        List<Project> projects = projectService.getTeacherProjects(currentUser.getId());
        return Result.success(projects);
    }

    @PutMapping("/{id}/review")
    @Operation(summary = "审核项目", description = "教师审核学生提交的项目")
    public Result<Boolean> reviewProject(
            @PathVariable Long id,
            @RequestParam Integer status,
            @RequestParam(required = false) String feedback) {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        
        // 验证是否为教师
        if (!"TEACHER".equals(currentUser.getRole()) && !"ADMIN".equals(currentUser.getRole())) {
            return Result.forbidden("只有教师可以审核项目");
        }
        
        // 获取项目信息
        Project project = projectService.getProjectById(id);
        if (project == null) {
            return Result.failed("项目不存在");
        }
        
        // 验证是否为项目指导教师
        if (!project.getTeacherId().equals(currentUser.getId())) {
            return Result.forbidden("无权限审核此项目");
        }
        
        // 审核项目
        boolean result = projectService.reviewProject(id, currentUser.getId(), status, feedback);
        return Result.success(result);
    }
}