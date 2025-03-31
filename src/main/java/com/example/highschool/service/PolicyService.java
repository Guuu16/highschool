package com.example.highschool.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.highschool.entity.Policy;

import java.util.List;

/**
 * 政策公告服务接口
 */
public interface PolicyService extends IService<Policy> {

    /**
     * 创建政策公告
     *
     * @param policy 政策公告信息
     * @return 创建的政策公告
     */
    Policy createPolicy(Policy policy);

    /**
     * 更新政策公告
     *
     * @param policy 政策公告信息
     * @return 更新后的政策公告
     */
    Policy updatePolicy(Policy policy);

    /**
     * 删除政策公告
     *
     * @param id 政策公告ID
     * @return 是否删除成功
     */
    boolean deletePolicy(Long id);

    /**
     * 根据ID获取政策公告
     *
     * @param id 政策公告ID
     * @return 政策公告信息
     */
    Policy getPolicyById(Long id);

    /**
     * 分页查询政策公告
     *
     * @param page 分页参数
     * @param title 标题（可选）
     * @return 分页结果
     */
    IPage<Policy> listPolicies(Page<Policy> page, String title);

    /**
     * 获取最新的政策公告
     *
     * @param limit 限制数量
     * @return 政策公告列表
     */
    List<Policy> getLatestPolicies(Integer limit);
}