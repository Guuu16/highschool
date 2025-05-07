package com.example.highschool.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 项目实体类
 */
@Data
@TableName("project")
public class Project implements Serializable {

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
     * 教师ID
     */
    private Long teacherId;

    /**
     * 项目标题
     */
    private String title;

    /**
     * 项目描述
     */
    private String description;

    /**
     * 项目类别ID
     */
    private Long categoryId;

    /**
     * 项目状态：0-待审核，1-已通过，2-已拒绝
     */
    private Integer status;

    /**
     * 反馈意见
     */
    private String feedback;

    /**
     * 计划文件URL
     */
    private String planFileUrl;

    /**
     * 学分
     */
    private Integer credit;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

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

    /**
     * 获取创建时间
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * 获取标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 获取学生ID
     */
    public Long getStudentId() {
        return studentId;
    }
    
    /**
     * 设置学生ID
     */
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    
    /**
     * 设置ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取教师ID
     */
    public Long getTeacherId() {
        return teacherId;
    }
    
    /**
     * 设置教师ID
     */
    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * 获取状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置反馈意见
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
