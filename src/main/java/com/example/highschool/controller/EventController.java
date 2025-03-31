package com.example.highschool.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.highschool.common.api.Result;
import com.example.highschool.entity.Event;
import com.example.highschool.entity.User;
import com.example.highschool.service.EventService;
import com.example.highschool.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 讲座/培训信息控制器
 */
@RestController
@RequestMapping("/events")
@Tag(name = "讲座培训管理", description = "讲座/培训信息的创建、更新、查询等接口")
public class EventController {

    @Autowired
    private EventService eventService;
    
    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "创建讲座/培训信息", description = "教师或管理员创建讲座/培训信息")
    public Result<Event> createEvent(@RequestBody Event event) {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return Result.unauthorized("用户未登录或会话已过期");
        }
        
        // 验证是否为教师或管理员
        if (!"TEACHER".equals(currentUser.getRole()) && !"ADMIN".equals(currentUser.getRole())) {
            return Result.forbidden("只有教师或管理员可以创建讲座/培训信息");
        }
        
        // 设置创建者
        event.setCreatedBy(currentUser.getId());
        
        // 创建讲座/培训信息
        Event createdEvent = eventService.createEvent(event);
        return Result.success(createdEvent);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新讲座/培训信息", description = "教师或管理员更新讲座/培训信息")
    public Result<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return Result.unauthorized("用户未登录或会话已过期");
        }
        
        // 获取原讲座/培训信息
        Event existingEvent = eventService.getEventById(id);
        if (existingEvent == null) {
            return Result.failed("讲座/培训信息不存在");
        }
        
        // 验证是否为创建者或管理员
        if (!existingEvent.getCreatedBy().equals(currentUser.getId()) && 
            !"ADMIN".equals(currentUser.getRole())) {
            return Result.forbidden("无权限修改此讲座/培训信息");
        }
        
        // 设置ID
        event.setId(id);
        
        // 更新讲座/培训信息
        Event updatedEvent = eventService.updateEvent(event);
        return Result.success(updatedEvent);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除讲座/培训信息", description = "教师或管理员删除讲座/培训信息")
    public Result<Boolean> deleteEvent(@PathVariable Long id) {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return Result.unauthorized("用户未登录或会话已过期");
        }
        
        // 获取原讲座/培训信息
        Event existingEvent = eventService.getEventById(id);
        if (existingEvent == null) {
            return Result.failed("讲座/培训信息不存在");
        }
        
        // 验证是否为创建者或管理员
        if (!existingEvent.getCreatedBy().equals(currentUser.getId()) && 
            !"ADMIN".equals(currentUser.getRole())) {
            return Result.forbidden("无权限删除此讲座/培训信息");
        }
        
        // 删除讲座/培训信息
        boolean result = eventService.deleteEvent(id);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取讲座/培训信息详情", description = "获取讲座/培训信息详情")
    public Result<Event> getEvent(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        if (event == null) {
            return Result.failed("讲座/培训信息不存在");
        }
        return Result.success(event);
    }

    @GetMapping
    @Operation(summary = "分页查询讲座/培训信息", description = "分页查询讲座/培训信息列表")
    public Result<IPage<Event>> listEvents(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String title) {
        Page<Event> page = new Page<>(pageNum, pageSize);
        IPage<Event> eventPage = eventService.listEvents(page, title);
        return Result.success(eventPage);
    }

    @GetMapping("/upcoming")
    @Operation(summary = "获取即将举行的讲座/培训信息", description = "获取即将举行的几条讲座/培训信息")
    public Result<List<Event>> getUpcomingEvents(@RequestParam(defaultValue = "5") Integer limit) {
        List<Event> events = eventService.getUpcomingEvents(limit);
        return Result.success(events);
    }
}