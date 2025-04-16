package com.example.highschool.controller;

import com.example.highschool.common.api.Result;
import com.example.highschool.entity.Category;
import com.example.highschool.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生项目类别控制器
 */
@RestController
@RequestMapping("/student/categories")
@Tag(name = "学生项目类别查询", description = "学生查询项目类别接口")
public class StudentCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    @Operation(summary = "获取类别详情", description = "学生获取项目类别详情")
    public Result<Category> getCategory(@PathVariable Long id) {
        return Result.success(categoryService.getById(id));
    }

    @GetMapping
    @Operation(summary = "获取所有类别", description = "学生获取所有项目类别列表")
    public Result<List<Category>> listCategories() {
        return Result.success(categoryService.list());
    }
}
