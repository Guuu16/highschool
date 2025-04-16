package com.example.highschool.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.highschool.entity.StudentDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学生详细信息Mapper接口
 */
@Mapper
public interface StudentDetailMapper extends BaseMapper<StudentDetail> {
}
