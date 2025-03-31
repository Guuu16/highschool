package com.example.highschool.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色：ADMIN-管理员，TEACHER-教师，STUDENT-学生
     */
    private String role;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 状态：0-禁用，1-启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 获取用户ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 获取用户角色
     */
    public String getRole() {
        return role;
    }
    
    /**
     * 设置用户角色
     */
    public void setRole(String role) {
        this.role = role;
    }
    
    /**
     * 获取用户名
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * 设置用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * 获取密码
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * 设置密码
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * 获取状态
     */
    public Integer getStatus() {
        return status;
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
     * 设置创建时间
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    
    /**
     * 获取更新时间
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    
    /**
     * 设置更新时间
     */
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}