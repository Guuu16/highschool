package com.example.highschool.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 学生导师申请实体类
 */
@Data
@TableName("mentor_application")
public class MentorApplication implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 学生ID
     */
    private Long studentId;

    /**
     * 导师ID
     */
    private Long teacherId;

    /**
     * 申请状态：0-待审核，1-已通过，2-已拒绝
     */
    private Integer status;

    /**
     * 申请时间
     */
    private LocalDateTime appliedAt;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 设置学生ID
     */
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    
    /**
     * 获取学生ID
     */
    public Long getStudentId() {
        return studentId;
    }
    
    /**
     * 获取教师ID
     */
    public Long getTeacherId() {
        return teacherId;
    }
    
    /**
     * 设置申请时间
     */
    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }
    
    /**
     * 获取申请时间
     */
    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }
    
    /**
     * 设置创建时间
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    
    /**
     * 设置更新时间
     */
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    
    /**
     * 设置状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}