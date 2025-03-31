package com.example.highschool.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.highschool.entity.Event;
import org.apache.ibatis.annotations.Mapper;

/**
 * 讲座/培训信息Mapper接口
 */
@Mapper
public interface EventMapper extends BaseMapper<Event> {
}