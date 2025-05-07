package com.example.highschool.controller;

import com.example.highschool.common.api.Result;
import com.example.highschool.dto.ProjectReviewDTO;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 教师控制器
 */
@RestController
@RequestMapping("/teacher")
@Tag(name = "教师功能", description = "教师特有的项目审核、学生指导等接口")
public class TeacherController {

    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private ProjectProgressService projectProgressService;
    
    @Autowired
    private MentorApplicationService mentorApplicationService;

    @Autowired
    private UserService userService;
    
    @GetMapping("/projects")
    @Operation(summary = "获取教师指导的项目", description = "获取当前登录教师指导的所有项目")
    public Result<List<Project>> getTeacherProjects() {
        // 获取当前教师ID的逻辑
        Long teacherId = getCurrentTeacherId();
        List<Project> projects = projectService.getTeacherProjects(teacherId);
        return Result.success(projects);
    }

    @PutMapping(value = "/projects/{id}/review", consumes = "application/json")
    @Operation(summary = "审核项目", description = "教师审核学生提交的项目")
    public Result<Boolean> reviewProject(
            @PathVariable Long id,
            @RequestParam Integer status,
            @RequestBody(required = false) ProjectReviewDTO reviewDTO) {
        // 处理空请求体
        if (reviewDTO == null) {
            reviewDTO = new ProjectReviewDTO();
            System.out.println("使用默认DTO对象");
        }
        
        System.out.println("项目审核请求参数 - 项目ID: " + id);
        System.out.println("DTO内容: " + reviewDTO);

        // 获取当前教师ID的逻辑
        Long teacherId = getCurrentTeacherId();
        System.out.println("当前教师ID: " + teacherId);
        
        boolean result = projectService.reviewProject(
            id, 
            teacherId, 
            status,
            reviewDTO != null ? reviewDTO.getFeedback() : null,
            reviewDTO != null ? reviewDTO.getCredit() : null
        );
        System.out.println("审核结果: " + result);
        
        return Result.success(result);
    }

    @GetMapping("/project/{id}/progress")
    @Operation(summary = "获取项目进度", description = "获取指定项目的所有进度记录")
    public Result<List<ProjectProgress>> getProjectProgress(@PathVariable Long id) {
        // 获取当前教师ID的逻辑
        Long teacherId = getCurrentTeacherId();
        // 验证项目是否属于当前教师
        Project project = projectService.getProjectById(id);
        if (project == null || !project.getTeacherId().equals(teacherId)) {
            return Result.failed("项目不存在或无权限查看");
        }
        List<ProjectProgress> progressList = projectProgressService.getProjectProgresses(id);
        return Result.success(progressList);
    }

    @PutMapping("/progress/{id}/feedback")
    @Operation(summary = "提交进度反馈", description = "教师对学生提交的项目进度提供反馈")
    public Result<Boolean> submitProgressFeedback(
            @PathVariable Long id,
            @RequestParam String feedback) {
        // 获取当前教师ID的逻辑
        Long teacherId = getCurrentTeacherId();
        boolean result = projectProgressService.submitFeedback(id, teacherId, feedback);
        return Result.success(result);
    }

    @GetMapping("/mentor-applications")
    @Operation(summary = "获取导师申请", description = "获取学生向当前教师提交的导师申请")
    public Result<List<MentorApplication>> getMentorApplications() {
        // 获取当前教师ID的逻辑
        Long teacherId = getCurrentTeacherId();
        List<MentorApplication> applications = mentorApplicationService.getTeacherApplications(teacherId);
        return Result.success(applications);
    }

    @PutMapping("/mentor-application/{id}/review")
    @Operation(summary = "审核导师申请", description = "教师审核学生提交的导师申请")
    public Result<Boolean> reviewMentorApplication(
            @PathVariable Long id,
            @RequestParam Integer status) {
        // 获取当前教师ID的逻辑
        Long teacherId = getCurrentTeacherId();
        boolean result = mentorApplicationService.reviewApplication(id, teacherId, status);
        return Result.success(result);
    }
    
    /**
     * 获取当前登录教师ID
     * 从SecurityContext中获取当前用户信息
     * @return 当前登录教师ID
     * @throws RuntimeException 当前用户不是教师或未登录时抛出异常
     */
    @GetMapping("/students")
    @Operation(summary = "获取学生列表", description = "获取所有学生列表(与/api/api/student/teachers接口对应)")
    public Result<List<User>> getStudentsByTeacher() {
        List<User> students = userService.getStudentList();
        // 不返回密码
        students.forEach(student -> student.setPassword(null));
        return Result.success(students);
    }

    private Long getCurrentTeacherId() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            throw new RuntimeException("用户未登录");
        }
        if (!"TEACHER".equals(currentUser.getRole()) && !"ADMIN".equals(currentUser.getRole())) {
            throw new RuntimeException("当前用户无教师权限");
        }
        return currentUser.getId();
    }
}
