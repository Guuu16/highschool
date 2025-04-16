package com.example.highschool.service.impl;

import com.example.highschool.dto.PasswordUpdateDTO;
import java.util.Map;
import java.util.HashMap;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.highschool.common.util.JwtTokenUtil;
import com.example.highschool.entity.User;
import com.example.highschool.mapper.UserMapper;
import com.example.highschool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public User register(User user) {
        // 检查用户名是否已存在
        User existUser = getUserByUsername(user.getUsername());
        if (existUser != null) {
            return null;
        }

        // 设置默认状态为启用
        user.setStatus(1);
        // 设置创建时间和更新时间
        LocalDateTime now = LocalDateTime.now();
        user.setCreateTime(now);
        user.setUpdateTime(now);
        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 保存用户
        save(user);
        return user;
    }

    @Override
    public Map<String, String> login(String username, String password) {
        // 根据用户名获取用户
        User user = getUserByUsername(username);
        if (user == null) {
            throw new BadCredentialsException("用户名不存在");
        }

        // 检查用户状态
        if (user.getStatus() == 0) {
            throw new BadCredentialsException("账号已被禁用");
        }

        // 验证密码
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }

        // 生成JWT令牌
        Map<String, String> result = new HashMap<>();
        result.put("token", jwtTokenUtil.generateToken(username, user.getRole()));
        result.put("role", user.getRole());
        return result;
    }

    @Override
    public User getUserByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return getOne(wrapper);
    }

    @Override
    public User getCurrentUser() {
        // 从安全上下文中获取当前用户名
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            return getUserByUsername(username);
        }
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        // 设置更新时间
        user.setUpdateTime(LocalDateTime.now());
        
        // 如果密码不为空，则加密密码
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        
        return updateById(user);
    }
    
    @Override
    public IPage<User> listUsers(Page<User> page, String username, String role) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        
        // 添加用户名查询条件
        if (username != null && !username.isEmpty()) {
            queryWrapper.like("username", username);
        }
        
        // 添加角色查询条件
        if (role != null && !role.isEmpty()) {
            queryWrapper.eq("role", role);
        }
        
        // 按创建时间降序排序
        queryWrapper.orderByDesc("create_time");
        
        return page(page, queryWrapper);
    }
    
    @Override
    public boolean updateUserStatus(Long id, Integer status) {
        User user = getById(id);
        if (user == null) {
            return false;
        }
        
        // 设置状态和更新时间
        user.setStatus(status);
        user.setUpdateTime(LocalDateTime.now());
        
        return updateById(user);
    }
    
    @Override
    public boolean updateUserRole(Long id, String role) {
        User user = getById(id);
        if (user == null) {
            return false;
        }
        
        // 设置角色和更新时间
        user.setRole(role);
        user.setUpdateTime(LocalDateTime.now());
        
        return updateById(user);
    }
    
    @Override
    public List<User> getTeacherList() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role", "TEACHER");
        queryWrapper.eq("status", 1); // 只查询启用状态的教师
        queryWrapper.orderByDesc("create_time");
        return list(queryWrapper);
    }
    
    @Override
    public boolean updatePassword(PasswordUpdateDTO passwordUpdateDTO) {
        // 获取当前用户
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return false;
        }
        
        // 验证旧密码
        if (!passwordEncoder.matches(passwordUpdateDTO.getOldPassword(), currentUser.getPassword())) {
            return false;
        }
        
        // 检查新密码和确认密码是否一致
        if (!passwordUpdateDTO.getNewPassword().equals(passwordUpdateDTO.getConfirmPassword())) {
            return false;
        }
        
        // 加密新密码并更新
        currentUser.setPassword(passwordEncoder.encode(passwordUpdateDTO.getNewPassword()));
        currentUser.setUpdateTime(LocalDateTime.now());
        
        return updateById(currentUser);
    }
}
