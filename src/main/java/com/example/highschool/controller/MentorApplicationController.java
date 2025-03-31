package com.example.highschool.controller;

import com.example.highschool.common.api.Result;
import com.example.highschool.entity.MentorApplication;
import com.example.highschool.entity.User;
import com.example.highschool.service.MentorApplicationService;
import com.example.highschool.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 导师申请控制器
 */
@RestController
@RequestMapping("/mentor-applications")
@Tag(name = "导师申请管理", description = "导师申请的创建、审核、查询等接口")
public class MentorApplicationController {

    @Autowired
    private MentorApplicationService mentorApplicationService;
    
    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "申请导师", description = "学生申请导师")
    public Result<MentorApplication> applyMentor(@RequestBody MentorApplication application) {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        
        // 验证是否为学生
        if (!"STUDENT".equals(currentUser.getRole())) {
            return Result.forbidden("只有学生可以申请导师");
        }
        
        // 设置学生ID
        application.setStudentId(currentUser.getId());
        
        // 申请导师
        MentorApplication createdApplication = mentorApplicationService.applyMentor(application);
        return Result.success(createdApplication);
    }

    @PutMapping("/{id}/review")
    @Operation(summary = "审核导师申请", description = "教师审核导师申请")
    public Result<Boolean> reviewApplication(
            @PathVariable Long id,
            @RequestParam Integer status) {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        
        // 验证是否为教师
        if (!"TEACHER".equals(currentUser.getRole()) && !"ADMIN".equals(currentUser.getRole())) {
            return Result.forbidden("只有教师可以审核导师申请");
        }
        
        // 审核申请
        boolean result = mentorApplicationService.reviewApplication(id, currentUser.getId(), status);
        if (!result) {
            return Result.failed("审核失败，申请不存在或无权限审核");
        }
        return Result.success(result);
    }

    @GetMapping("/student")
    @Operation(summary = "获取学生的导师申请", description = "获取当前登录学生的所有导师申请")
    public Result<List<MentorApplication>> getStudentApplications() {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        
        // 验证是否为学生
        if (!"STUDENT".equals(currentUser.getRole())) {
            return Result.forbidden("只有学生可以查看自己的导师申请");
        }
        
        // 获取学生的导师申请
        List<MentorApplication> applications = mentorApplicationService.getStudentApplications(currentUser.getId());
        return Result.success(applications);
    }

    @GetMapping("/teacher")
    @Operation(summary = "获取教师的导师申请", description = "获取当前登录教师收到的所有导师申请")
    public Result<List<MentorApplication>> getTeacherApplications() {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        
        // 验证是否为教师
        if (!"TEACHER".equals(currentUser.getRole()) && !"ADMIN".equals(currentUser.getRole())) {
            return Result.forbidden("只有教师可以查看收到的导师申请");
        }
        
        // 获取教师的导师申请
        List<MentorApplication> applications = mentorApplicationService.getTeacherApplications(currentUser.getId());
        return Result.success(applications);
    }
}