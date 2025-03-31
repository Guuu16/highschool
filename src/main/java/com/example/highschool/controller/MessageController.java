package com.example.highschool.controller;

import com.example.highschool.common.api.Result;
import com.example.highschool.entity.Message;
import com.example.highschool.entity.User;
import com.example.highschool.service.MessageService;
import com.example.highschool.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 师生沟通消息控制器
 */
@RestController
@RequestMapping("/messages")
@Tag(name = "师生沟通消息", description = "师生沟通消息的发送、查询等接口")
public class MessageController {

    @Autowired
    private MessageService messageService;
    
    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "发送消息", description = "发送消息给指定用户")
    public Result<Message> sendMessage(@RequestBody Message message) {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        
        // 设置发送者
        message.setSenderId(currentUser.getId());
        
        // 发送消息
        Message sentMessage = messageService.sendMessage(message);
        return Result.success(sentMessage);
    }

    @GetMapping("/received")
    @Operation(summary = "获取收到的消息", description = "获取当前用户收到的所有消息")
    public Result<List<Message>> getReceivedMessages() {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        
        // 获取收到的消息
        List<Message> messages = messageService.getReceivedMessages(currentUser.getId());
        return Result.success(messages);
    }

    @GetMapping("/sent")
    @Operation(summary = "获取发送的消息", description = "获取当前用户发送的所有消息")
    public Result<List<Message>> getSentMessages() {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        
        // 获取发送的消息
        List<Message> messages = messageService.getSentMessages(currentUser.getId());
        return Result.success(messages);
    }

    @GetMapping("/conversation/{userId}")
    @Operation(summary = "获取与指定用户的对话", description = "获取当前用户与指定用户之间的所有消息")
    public Result<List<Message>> getConversation(@PathVariable Long userId) {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        
        // 获取对话消息
        List<Message> messages = messageService.getConversation(currentUser.getId(), userId);
        return Result.success(messages);
    }

    @PutMapping("/{id}/read")
    @Operation(summary = "标记消息为已读", description = "标记指定消息为已读状态")
    public Result<Boolean> markAsRead(@PathVariable Long id) {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        
        // 标记为已读
        boolean result = messageService.markAsRead(id, currentUser.getId());
        return Result.success(result);
    }

    @GetMapping("/unread/count")
    @Operation(summary = "获取未读消息数量", description = "获取当前用户的未读消息数量")
    public Result<Map<String, Integer>> getUnreadCount() {
        // 获取当前用户
        User currentUser = userService.getCurrentUser();
        
        // 获取未读消息数量
        int count = messageService.getUnreadCount(currentUser.getId());
        return Result.success(Map.of("count", count));
    }
}