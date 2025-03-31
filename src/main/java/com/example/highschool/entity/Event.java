package com.example.highschool.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 讲座/培训信息实体类
 */
@Data
@TableName("event")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 活动标题
     */
    private String title;

    /**
     * 活动内容
     */
    private String description;

    /**
     * 活动时间
     */
    private LocalDateTime eventTime;

    /**
     * 资源链接
     */
    private String resourceUrl;

    /**
     * 创建者ID
     */
    private Long createdBy;

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
     * 获取标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 获取活动时间
     */
    public LocalDateTime getEventTime() {
        return eventTime;
    }

    /**
     * 设置ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 设置创建者ID
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 获取创建者ID
     */
    public Long getCreatedBy() {
        return createdBy;
    }
}