package com.example.highschool.controller;

import com.example.highschool.dto.UserStatusDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.highschool.common.api.Result;
import com.example.highschool.entity.User;
import com.example.highschool.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员控制器
 */
@RestController
@RequestMapping("/admin")
@Tag(name = "管理员功能", description = "管理员特有的用户管理、系统设置等接口")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/list")
    @Operation(summary = "分页查询用户", description = "管理员分页查询所有用户")
    public Result<IPage<User>> listUsers(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String role) {
        Page<User> page = new Page<>(pageNum, pageSize);
        IPage<User> userPage = userService.listUsers(page, username, role);
        // 不返回密码
        userPage.getRecords().forEach(user -> user.setPassword(null));
        return Result.success(userPage);
    }

    @PutMapping("/user/status")
    @Operation(summary = "修改用户状态", description = "管理员修改用户启用/禁用状态")
    public Result<Boolean> updateUserStatus(@RequestBody UserStatusDTO userStatusDTO) {
        boolean result = userService.updateUserStatus(userStatusDTO.getId(), userStatusDTO.getStatus());
        return Result.success(result);
    }

    @PutMapping("/user/{id}/role")
    @Operation(summary = "修改用户角色", description = "管理员修改用户角色")
    public Result<Boolean> updateUserRole(@PathVariable Long id, @RequestParam String role) {
        boolean result = userService.updateUserRole(id, role);
        return Result.success(result);
    }

    @DeleteMapping("/user/{id}")
    @Operation(summary = "删除用户", description = "管理员删除指定用户")
    public Result<Boolean> deleteUser(@PathVariable Long id) {
        boolean result = userService.removeById(id);
        return Result.success(result);
    }

    @GetMapping("/dashboard/stats")
    @Operation(summary = "获取系统统计数据", description = "管理员获取系统统计数据")
    public Result<Object> getDashboardStats() {
        // 这里可以实现获取系统统计数据的逻辑
        // 例如：用户总数、项目总数、活跃用户数等
        return Result.success(null);
    }
}
