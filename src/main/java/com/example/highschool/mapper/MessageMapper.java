package com.example.highschool.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.highschool.entity.Message;
import org.apache.ibatis.annotations.Mapper;

/**
 * 师生沟通消息Mapper接口
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}