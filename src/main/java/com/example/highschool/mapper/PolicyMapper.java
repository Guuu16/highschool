package com.example.highschool.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.highschool.entity.Policy;
import org.apache.ibatis.annotations.Mapper;

/**
 * 政策公告Mapper接口
 */
@Mapper
public interface PolicyMapper extends BaseMapper<Policy> {
}