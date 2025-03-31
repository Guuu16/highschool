package com.example.highschool.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.highschool.entity.Event;

import java.util.List;

/**
 * 讲座/培训信息服务接口
 */
public interface EventService extends IService<Event> {

    /**
     * 创建讲座/培训信息
     *
     * @param event 讲座/培训信息
     * @return 创建的讲座/培训信息
     */
    Event createEvent(Event event);

    /**
     * 更新讲座/培训信息
     *
     * @param event 讲座/培训信息
     * @return 更新后的讲座/培训信息
     */
    Event updateEvent(Event event);

    /**
     * 删除讲座/培训信息
     *
     * @param id 讲座/培训信息ID
     * @return 是否删除成功
     */
    boolean deleteEvent(Long id);

    /**
     * 根据ID获取讲座/培训信息
     *
     * @param id 讲座/培训信息ID
     * @return 讲座/培训信息
     */
    Event getEventById(Long id);

    /**
     * 分页查询讲座/培训信息
     *
     * @param page 分页参数
     * @param title 标题（可选）
     * @return 分页结果
     */
    IPage<Event> listEvents(Page<Event> page, String title);

    /**
     * 获取即将举行的讲座/培训信息
     *
     * @param limit 限制数量
     * @return 讲座/培训信息列表
     */
    List<Event> getUpcomingEvents(Integer limit);
}