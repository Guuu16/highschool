package com.example.highschool.service;

import com.example.highschool.dto.PasswordUpdateDTO;
import java.util.Map;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.highschool.entity.User;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return 注册结果
     */
    User register(User user);

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回JWT令牌，失败返回null
     */
    Map<String, String> login(String username, String password);

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    User getUserByUsername(String username);

    /**
     * 获取当前登录用户信息
     *
     * @return 用户信息
     */
    User getCurrentUser();

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return 更新结果
     */
    boolean updateUser(User user);
    
    /**
     * 分页查询用户列表
     *
     * @param page 分页参数
     * @param username 用户名（可选）
     * @param role 角色（可选）
     * @return 用户分页列表
     */
    IPage<User> listUsers(Page<User> page, String username, String role);
    
    /**
     * 更新用户状态
     *
     * @param id 用户ID
     * @param status 状态：0-禁用，1-启用
     * @return 更新结果
     */
    boolean updateUserStatus(Long id, Integer status);
    
    /**
     * 更新用户角色
     *
     * @param id 用户ID
     * @param role 角色
     * @return 更新结果
     */
    boolean updateUserRole(Long id, String role);
    
    /**
     * 获取所有教师列表
     *
     * @return 教师列表
     */
    List<User> getTeacherList();
    
    /**
     * 修改密码
     *
     * @param passwordUpdateDTO 密码更新DTO
     * @return 修改结果
     */
    boolean updatePassword(PasswordUpdateDTO passwordUpdateDTO);
}
