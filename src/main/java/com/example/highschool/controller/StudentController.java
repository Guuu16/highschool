package com.example.highschool.controller;

import com.example.highschool.common.api.Result;
import com.example.highschool.entity.MentorApplication;
import com.example.highschool.entity.Project;
import com.example.highschool.entity.ProjectProgress;
import com.example.highschool.entity.User;
import com.example.highschool.service.MentorApplicationService;
import com.example.highschool.service.ProjectProgressService;
import com.example.highschool.service.ProjectService;
import com.example.highschool.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 学生控制器
 */
@RestController
@RequestMapping("/student")
@Tag(name = "学生功能", description = "学生特有的项目申请、进度提交等接口")
public class StudentController {

    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private ProjectProgressService projectProgressService;
    
    @Autowired
    private MentorApplicationService mentorApplicationService;
    
    @Autowired
    private UserService userService;

    // @PostMapping("/project")
    // @Operation(summary = "创建项目", description = "学生创建新的创新创业项目")
    // public Result<Project> createProject(@RequestBody Project project) {
    //     // 获取当前学生ID
    //     Long studentId = getCurrentStudentId();
    //     project.setStudentId(studentId);
    //     Project createdProject = projectService.createProject(project);
    //     return Result.success(createdProject);
    // }

    @GetMapping("/projects")
    @Operation(summary = "获取学生的项目", description = "获取当前登录学生的所有项目")
    public Result<List<Project>> getStudentProjects() {
        // 获取当前学生ID
        Long studentId = getCurrentStudentId();
        List<Project> projects = projectService.getStudentProjects(studentId);
        return Result.success(projects);
    }

    @PostMapping(value = "/project/{id}/progress", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "提交项目进度", description = "学生提交项目进度")
    public Result<ProjectProgress> submitProjectProgress(
            @PathVariable Long id,
            @RequestParam Integer progressPercent,
            @RequestParam String phaseTitle,
            @RequestParam String description,
            @RequestParam(required = false) MultipartFile file) {
        // 获取当前学生ID
        Long studentId = getCurrentStudentId();
        System.out.println("当前学生ID: " + studentId);
        // 验证项目是否属于当前学生
        Project project = projectService.getProjectById(id);
        System.out.println("查询到的项目: " + (project != null ? project.getId() : "null"));
        if (project == null) {
            return Result.failed("项目不存在");
        }
        if (!project.getStudentId().equals(studentId)) {
            System.out.println("权限验证失败: 项目学生ID=" + project.getStudentId() + ", 当前学生ID=" + studentId);
            return Result.failed("无权限操作该项目");
        }
        
        ProjectProgress progress = new ProjectProgress();
        progress.setProjectId(id);
        progress.setProgress(progressPercent);
        progress.setPhaseTitle(phaseTitle);
        progress.setDescription(description);
        
        // 如果有文件上传，处理文件上传逻辑
        if (file != null && !file.isEmpty()) {
            try {
                String fileUrl = projectService.uploadPlanFile(file);
                progress.setFileUrl(fileUrl);
            } catch (IOException e) {
                return Result.failed("文件上传失败: " + e.getMessage());
            }
        }
        
        ProjectProgress savedProgress = projectProgressService.submitProgress(progress);
        return Result.success(savedProgress);
    }

    @GetMapping("/project/{id}/progress")
    @Operation(summary = "获取项目进度", description = "获取指定项目的所有进度记录")
    public Result<List<ProjectProgress>> getProjectProgress(@PathVariable Long id) {
        // 获取当前学生ID
        Long studentId = getCurrentStudentId();
        // 验证项目是否属于当前学生
        Project project = projectService.getProjectById(id);
        if (project == null || !project.getStudentId().equals(studentId)) {
            return Result.failed("项目不存在或无权限查看");
        }
        List<ProjectProgress> progressList = projectProgressService.getProjectProgresses(id);
        return Result.success(progressList);
    }

    @GetMapping("/teachers")
    @Operation(summary = "获取教师列表", description = "获取所有可选的指导教师列表")
    public Result<List<User>> getTeacherList() {
        List<User> teachers = userService.getTeacherList();
        // 不返回密码
        teachers.forEach(teacher -> teacher.setPassword(null));
        return Result.success(teachers);
    }

    @PostMapping("/mentor-application")
    @Operation(summary = "申请导师", description = "学生申请指定教师作为导师")
    public Result<MentorApplication> applyMentor(@RequestBody MentorApplication application) {
        // 获取当前学生ID
        Long studentId = getCurrentStudentId();
        application.setStudentId(studentId);
        MentorApplication savedApplication = mentorApplicationService.applyMentor(application);
        return Result.success(savedApplication);
    }

    @GetMapping("/mentor-applications")
    @Operation(summary = "获取导师申请", description = "获取当前学生的所有导师申请记录")
    public Result<List<MentorApplication>> getMentorApplications() {
        // 获取当前学生ID
        Long studentId = getCurrentStudentId();
        List<MentorApplication> applications = mentorApplicationService.getStudentApplications(studentId);
        return Result.success(applications);
    }
    
    /**
     * 获取当前登录学生ID
     */
    private Long getCurrentStudentId() {
        return userService.getCurrentUser().getId();
    }
    
    /**
     * 上传文件
     * 实际实现应该处理文件上传逻辑
     */
    private String uploadFile(MultipartFile file) {
        // 这里应该实现文件上传逻辑，返回文件URL
        // 为了示例，这里直接返回一个假的URL
        return "/uploads/" + file.getOriginalFilename();
    }
}
