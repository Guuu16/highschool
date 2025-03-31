package com.example.highschool.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.highschool.entity.Project;
import org.apache.ibatis.annotations.Mapper;

/**
 * 项目Mapper接口
 */
@Mapper
public interface ProjectMapper extends BaseMapper<Project> {
}