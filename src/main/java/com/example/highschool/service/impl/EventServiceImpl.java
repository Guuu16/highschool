package com.example.highschool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.highschool.entity.Event;
import com.example.highschool.mapper.EventMapper;
import com.example.highschool.service.EventService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 讲座/培训信息服务实现类
 */
@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements EventService {

    @Override
    public Event createEvent(Event event) {
        // 设置创建时间和更新时间
        LocalDateTime now = LocalDateTime.now();
        event.setCreateTime(now);
        event.setUpdateTime(now);
        
        // 保存讲座/培训信息
        save(event);
        return event;
    }

    @Override
    public Event updateEvent(Event event) {
        // 设置更新时间
        event.setUpdateTime(LocalDateTime.now());
        
        // 更新讲座/培训信息
        updateById(event);
        return event;
    }

    @Override
    public boolean deleteEvent(Long id) {
        return removeById(id);
    }

    @Override
    public Event getEventById(Long id) {
        return getById(id);
    }

    @Override
    public IPage<Event> listEvents(Page<Event> page, String title) {
        LambdaQueryWrapper<Event> queryWrapper = new LambdaQueryWrapper<>();
        
        // 如果标题不为空，添加标题查询条件
        if (StringUtils.hasText(title)) {
            queryWrapper.like(Event::getTitle, title);
        }
        
        // 按活动时间降序排序
        queryWrapper.orderByDesc(Event::getEventTime);
        
        return page(page, queryWrapper);
    }

    @Override
    public List<Event> getUpcomingEvents(Integer limit) {
        LambdaQueryWrapper<Event> queryWrapper = new LambdaQueryWrapper<>();
        
        // 查询当前时间之后的活动
        LocalDateTime now = LocalDateTime.now();
        queryWrapper.ge(Event::getEventTime, now);
        
        // 按活动时间升序排序
        queryWrapper.orderByAsc(Event::getEventTime);
        
        // 设置查询数量限制
        Page<Event> page = new Page<>(1, limit);
        
        // 执行查询
        IPage<Event> eventPage = page(page, queryWrapper);
        
        return eventPage.getRecords();
    }
}