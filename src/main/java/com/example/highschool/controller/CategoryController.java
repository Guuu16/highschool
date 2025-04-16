package com.example.highschool.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.highschool.common.api.Result;
import com.example.highschool.entity.Category;
import com.example.highschool.entity.User;
import com.example.highschool.service.CategoryService;
import com.example.highschool.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目类别控制器（管理员专用）
 */
@RestController
@RequestMapping("/admin/categories")
@Tag(name = "项目类别管理", description = "项目类别的增删改查（管理员专用）")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "创建项目类别", description = "管理员创建新的项目类别")
    public Result<Category> createCategory(@RequestBody Category category) {
        checkAdminPermission();
        categoryService.save(category);
        return Result.success(category);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新项目类别", description = "管理员更新项目类别信息")
    public Result<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        checkAdminPermission();
        category.setId(id);
        categoryService.updateById(category);
        return Result.success(category);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除项目类别", description = "管理员删除项目类别")
    public Result<Boolean> deleteCategory(@PathVariable Long id) {
        checkAdminPermission();
        return Result.success(categoryService.removeById(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取类别详情", description = "获取项目类别详情")
    public Result<Category> getCategory(@PathVariable Long id) {
        return Result.success(categoryService.getById(id));
    }

    @GetMapping
    @Operation(summary = "获取所有类别", description = "获取所有项目类别列表")
    public Result<List<Category>> listCategories() {
        return Result.success(categoryService.list());
    }

    /**
     * 检查当前用户是否为管理员
     */
    private void checkAdminPermission() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
            throw new RuntimeException("无权限操作，需要管理员权限");
        }
    }
}
