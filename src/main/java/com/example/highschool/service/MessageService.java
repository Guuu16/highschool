package com.example.highschool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.highschool.entity.Message;

import java.util.List;

/**
 * 师生沟通消息服务接口
 */
public interface MessageService extends IService<Message> {

    /**
     * 发送消息
     *
     * @param message 消息信息
     * @return 发送的消息
     */
    Message sendMessage(Message message);

    /**
     * 获取用户收到的消息
     *
     * @param userId 用户ID
     * @return 消息列表
     */
    List<Message> getReceivedMessages(Long userId);

    /**
     * 获取用户发送的消息
     *
     * @param userId 用户ID
     * @return 消息列表
     */
    List<Message> getSentMessages(Long userId);

    /**
     * 获取用户与特定用户的对话消息
     *
     * @param userId 用户ID
     * @param otherUserId 对方用户ID
     * @return 消息列表
     */
    List<Message> getConversation(Long userId, Long otherUserId);

    /**
     * 标记消息为已读
     *
     * @param messageId 消息ID
     * @param userId 用户ID（接收者）
     * @return 是否成功
     */
    boolean markAsRead(Long messageId, Long userId);

    /**
     * 获取用户未读消息数量
     *
     * @param userId 用户ID
     * @return 未读消息数量
     */
    int getUnreadCount(Long userId);
}