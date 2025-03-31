package com.example.highschool.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 项目进度实体类
 */
@Data
@TableName("project_progress")
public class ProjectProgress implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 项目ID
     */
    private Long projectId;

    /**
     * 阶段标题
     */
    private String phaseTitle;

    /**
     * 阶段描述
     */
    private String description;

    /**
     * 附件路径
     */
    private String fileUrl;

    /**
     * 教师反馈
     */
    private String feedback;

    /**
     * 提交时间
     */
    private LocalDateTime submittedAt;

    /**
     * 设置提交时间
     */
    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
    
    /**
     * 获取项目ID
     */
    public Long getProjectId() {
        return projectId;
    }
    
    /**
     * 设置项目ID
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    
    /**
     * 设置ID
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * 获取ID
     */
    public Long getId() {
        return id;
    }
    
    /**
     * 获取文件URL
     */
    public String getFileUrl() {
        return fileUrl;
    }
    
    /**
     * 设置文件URL
     */
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    
    /**
     * 获取提交时间
     */
    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }
    
    /**
     * 获取阶段标题
     */
    public String getPhaseTitle() {
        return phaseTitle;
    }
    
    /**
     * 设置阶段标题
     */
    public void setPhaseTitle(String phaseTitle) {
        this.phaseTitle = phaseTitle;
    }
    
    /**
     * 获取阶段描述
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * 设置阶段描述
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * 获取教师反馈
     */
    public String getFeedback() {
        return feedback;
    }
    
    /**
     * 设置教师反馈
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}