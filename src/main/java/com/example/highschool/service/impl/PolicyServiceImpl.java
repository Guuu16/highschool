package com.example.highschool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.highschool.entity.Policy;
import com.example.highschool.mapper.PolicyMapper;
import com.example.highschool.service.PolicyService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 政策公告服务实现类
 */
@Service
public class PolicyServiceImpl extends ServiceImpl<PolicyMapper, Policy> implements PolicyService {

    @Override
    public Policy createPolicy(Policy policy) {
        // 设置发布时间和创建时间
        LocalDateTime now = LocalDateTime.now();
        policy.setPublishedAt(now);
        policy.setCreateTime(now);
        policy.setUpdateTime(now);
        
        // 保存政策公告
        save(policy);
        return policy;
    }

    @Override
    public Policy updatePolicy(Policy policy) {
        // 设置更新时间
        policy.setUpdateTime(LocalDateTime.now());
        
        // 更新政策公告
        updateById(policy);
        return policy;
    }

    @Override
    public boolean deletePolicy(Long id) {
        return removeById(id);
    }

    @Override
    public Policy getPolicyById(Long id) {
        return getById(id);
    }

    @Override
    public IPage<Policy> listPolicies(Page<Policy> page, String title) {
        LambdaQueryWrapper<Policy> queryWrapper = new LambdaQueryWrapper<>();
        
        // 如果标题不为空，添加标题查询条件
        if (StringUtils.hasText(title)) {
            queryWrapper.like(Policy::getTitle, title);
        }
        
        // 按发布时间降序排序
        queryWrapper.orderByDesc(Policy::getPublishedAt);
        
        return page(page, queryWrapper);
    }

    @Override
    public List<Policy> getLatestPolicies(Integer limit) {
        LambdaQueryWrapper<Policy> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Policy::getPublishedAt);
        
        // 设置查询数量限制
        Page<Policy> page = new Page<>(1, limit);
        
        // 执行查询
        IPage<Policy> policyPage = page(page, queryWrapper);
        
        return policyPage.getRecords();
    }
}