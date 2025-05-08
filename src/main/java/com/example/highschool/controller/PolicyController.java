package com.example.highschool.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.highschool.common.api.Result;
import com.example.highschool.entity.Policy;
import com.example.highschool.entity.User;
import com.example.highschool.service.PolicyService;
import com.example.highschool.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.highschool.dto.PolicyDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 政策公告控制器
 */
@RestController
@RequestMapping("/policies")
@Tag(name = "政策公告管理", description = "政策公告的创建、更新、查询等接口")
public class PolicyController {

    @Autowired
    private PolicyService policyService;
    
    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "创建政策公告", description = "管理员创建政策公告")
    public Result<Policy> createPolicy(@RequestBody PolicyDTO policyDTO) {
        System.out.println("Received publishedAt: " + policyDTO.getPublishedAt()); // 添加调试日志
        // 转换DTO到Entity
        Policy policy = new Policy();
        policy.setTitle(policyDTO.getTitle());
        policy.setContent(policyDTO.getContent());
        // 确保使用前端传递的时间
        if (policyDTO.getPublishedAt() != null) {
            policy.setPublishedAt(policyDTO.getPublishedAt());
        } else {
            policy.setPublishedAt(LocalDateTime.now());
        }
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return Result.unauthorized("用户未登录或会话已过期");
        }
        
        // 验证是否为管理员
        if (!"ADMIN".equals(currentUser.getRole())) {
            return Result.forbidden("只有管理员可以创建政策公告");
        }
        
        // 设置创建者
        policy.setCreatedBy(currentUser.getId());
        
        // 确保使用前端传递的时间（不再覆盖）
        
        // 创建政策公告
        Policy createdPolicy = policyService.createPolicy(policy);
        return Result.success(createdPolicy);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新政策公告", description = "管理员更新政策公告")
    public Result<Policy> updatePolicy(@PathVariable Long id, @RequestBody Policy policy) {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return Result.unauthorized("用户未登录或会话已过期");
        }
        
        // 验证是否为管理员
        if (!"ADMIN".equals(currentUser.getRole())) {
            return Result.forbidden("只有管理员可以更新政策公告");
        }
        
        // 设置ID
        policy.setId(id);
        
        // 更新政策公告
        Policy updatedPolicy = policyService.updatePolicy(policy);
        return Result.success(updatedPolicy);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除政策公告", description = "管理员删除政策公告")
    public Result<Boolean> deletePolicy(@PathVariable Long id) {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return Result.unauthorized("用户未登录或会话已过期");
        }
        
        // 验证是否为管理员
        if (!"ADMIN".equals(currentUser.getRole())) {
            return Result.forbidden("只有管理员可以删除政策公告");
        }
        
        // 删除政策公告
        boolean result = policyService.deletePolicy(id);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取政策公告详情", description = "获取政策公告详情")
    public Result<Policy> getPolicy(@PathVariable Long id) {
        Policy policy = policyService.getPolicyById(id);
        if (policy == null) {
            return Result.failed("政策公告不存在");
        }
        return Result.success(policy);
    }

    @GetMapping
    @Operation(summary = "分页查询政策公告", description = "分页查询政策公告列表")
    public Result<IPage<Policy>> listPolicies(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String title) {
        Page<Policy> page = new Page<>(pageNum, pageSize);
        IPage<Policy> policyPage = policyService.listPolicies(page, title);
        return Result.success(policyPage);
    }

    @GetMapping("/latest")
    @Operation(summary = "获取最新政策公告", description = "获取最新的几条政策公告")
    public Result<List<Policy>> getLatestPolicies(@RequestParam(defaultValue = "1") Integer limit) {
        List<Policy> policies = policyService.getLatestPolicies(limit);
        return Result.success(policies);
    }}
