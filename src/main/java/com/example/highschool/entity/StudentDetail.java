package com.example.highschool.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 学生详细信息实体类
 */
@Data
@TableName("student_detail")
public class StudentDetail {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long studentId; // 关联user表的id
    
    private String studentNumber; // 学号
    
    private String college; // 学院
    
    private String major; // 专业
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
}
