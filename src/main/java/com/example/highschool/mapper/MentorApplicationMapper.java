package com.example.highschool.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.highschool.entity.MentorApplication;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学生导师申请Mapper接口
 */
@Mapper
public interface MentorApplicationMapper extends BaseMapper<MentorApplication> {
}