package com.example.highschool.controller;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import com.example.highschool.dto.ProjectDTO;
import com.example.highschool.dto.ProjectReviewDTO;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建项目", description = "学生创建项目(支持文件上传)")
    @ResponseBody
    public Result<Project> createProject(
            @RequestParam Long teacherId,
            @RequestParam Long categoryId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam(required = false) Integer credit,
            @RequestParam(required = false) MultipartFile planFile) {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        
        // 验证是否为学生
        if (!"STUDENT".equals(currentUser.getRole())) {
            return Result.forbidden("只有学生可以创建项目");
        }
        
        // 创建项目DTO并设置字段
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setStudentId(currentUser.getId());
        projectDTO.setTeacherId(teacherId);
        projectDTO.setCategoryId(categoryId);
        projectDTO.setTitle(title);
        projectDTO.setDescription(description);
        projectDTO.setStatus(0); // 默认状态
        projectDTO.setCredit(credit != null ? credit : 0); // 使用传入的学分或默认为0
        
        // 处理文件上传
        if (planFile != null && !planFile.isEmpty()) {
            System.out.println("接收到文件: " + planFile.getOriginalFilename() + ", 大小: " + planFile.getSize());
            try {
                String fileUrl = projectService.uploadPlanFile(planFile);
                System.out.println("文件保存路径: " + fileUrl);
                projectDTO.setPlanFileUrl(fileUrl);
            } catch (IOException e) {
                System.err.println("文件上传失败: ");
                e.printStackTrace();
                return Result.failed("文件上传失败: " + e.getMessage());
            }
        } else {
            System.out.println("未接收到文件或文件为空");
        }
        
        // 创建项目
        Project createdProject = projectService.createProject(projectDTO);
        return Result.success(createdProject);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "更新项目", description = "学生更新项目(支持文件上传)")
    public Result<Project> updateProject(
            @PathVariable Long id,
            @RequestParam Long teacherId,
            @RequestParam Long categoryId,
            @RequestParam String title,
            @RequestParam Integer status,
            @RequestParam String description,
            @RequestParam(required = false) Integer credit,
            @RequestParam(required = false) MultipartFile planFile) {
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

        // 处理文件上传
        String fileUrl = existingProject.getPlanFileUrl();
        if (planFile != null && !planFile.isEmpty()) {
            try {
                fileUrl = projectService.uploadPlanFile(planFile);
            } catch (IOException e) {
                return Result.failed("文件上传失败: " + e.getMessage());
            }
        }
        
        // 创建项目DTO并更新字段
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(id);
        projectDTO.setStudentId(currentUser.getId());
        projectDTO.setTeacherId(teacherId);
        projectDTO.setCategoryId(categoryId);
        projectDTO.setTitle(title);
        projectDTO.setStatus(status);
        projectDTO.setDescription(description);
        projectDTO.setCredit(credit != null ? credit : existingProject.getCredit());
        projectDTO.setPlanFileUrl(fileUrl);
        
        // 更新项目
        Project updatedProject = projectService.updateProject(projectDTO);
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
        if (currentUser == null) {
            return Result.failed("未获取到当前用户信息，请检查是否已登录");
        }
        
        // 验证是否为学生
        if (!"STUDENT".equals(currentUser.getRole())) {
            return Result.forbidden("只有学生可以查看自己的项目");
        }
        
        // 获取学生项目
        List<Project> projects = projectService.getStudentProjects(currentUser.getId());
        return Result.success(projects);
    }

    @PostMapping(value = "/student", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "学生提交项目", description = "学生提交项目(支持文件上传)")
    public Result<Project> submitStudentProject(
            @RequestPart Long categoryId,
            @RequestPart Long teacherId,
            @RequestPart String title,
            @RequestPart String description,
            @RequestPart Integer status,
            @RequestPart Integer credit,
            @RequestPart("planFile") MultipartFile planFile) {
        
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return Result.failed("未获取到当前用户信息，请检查是否已登录");
        }
        
        // 验证是否为学生
        if (!"STUDENT".equals(currentUser.getRole())) {
            return Result.forbidden("只有学生可以提交项目");
        }
        
        try {
            // 处理文件上传
            String fileUrl = projectService.uploadPlanFile(planFile);
            
            // 创建项目DTO
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setStudentId(currentUser.getId());
            projectDTO.setTeacherId(teacherId);
            projectDTO.setTitle(title);
            projectDTO.setDescription(description);
            projectDTO.setCategoryId(categoryId);
            projectDTO.setStatus(status);
            projectDTO.setCredit(credit);
            projectDTO.setPlanFileUrl(fileUrl);
            
            // 创建项目
            Project project = projectService.createProject(projectDTO);
            return Result.success(project);
        } catch (IOException e) {
            return Result.failed("文件上传失败: " + e.getMessage());
        }
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
            @RequestBody ProjectReviewDTO reviewDTO) {
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
        boolean result = projectService.reviewProject(
            id, 
            currentUser.getId(), 
            reviewDTO.getStatus(), 
            reviewDTO.getFeedback(), 
            reviewDTO.getCredit()
        );
        return Result.success(result);
    }
}
