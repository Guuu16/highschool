package com.example.highschool.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.highschool.entity.Category;
import com.example.highschool.mapper.CategoryMapper;
import com.example.highschool.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * 项目类别服务实现类
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
