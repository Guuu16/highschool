package com.example.highschool.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 师生沟通消息实体类
 */
@Data
@TableName("message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 发送者ID
     */
    private Long senderId;

    /**
     * 接收者ID
     */
    private Long receiverId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送时间
     */
    private LocalDateTime sentAt;

    /**
     * 是否已读
     */
    private Boolean isRead;
    
    /**
     * 获取发送者ID
     */
    public Long getSenderId() {
        return senderId;
    }
    
    /**
     * 设置发送者ID
     */
    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }
    
    /**
     * 获取接收者ID
     */
    public Long getReceiverId() {
        return receiverId;
    }
    
    /**
     * 设置接收者ID
     */
    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }
    
    /**
     * 获取发送时间
     */
    public LocalDateTime getSentAt() {
        return sentAt;
    }
    
    /**
     * 设置发送时间
     */
    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
    
    /**
     * 获取是否已读
     */
    public Boolean getIsRead() {
        return isRead;
    }
    
    /**
     * 设置是否已读
     */
    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
}