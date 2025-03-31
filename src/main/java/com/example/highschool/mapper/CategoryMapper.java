package com.example.highschool.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.highschool.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * 项目类别Mapper接口
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}