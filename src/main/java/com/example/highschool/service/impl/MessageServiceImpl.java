package com.example.highschool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.highschool.entity.Message;
import com.example.highschool.mapper.MessageMapper;
import com.example.highschool.service.MessageService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 师生沟通消息服务实现类
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Override
    public Message sendMessage(Message message) {
        // 设置发送时间和未读状态
        message.setSentAt(LocalDateTime.now());
        message.setIsRead(Boolean.FALSE);
        
        // 保存消息
        save(message);
        return message;
    }

    @Override
    public List<Message> getReceivedMessages(Long userId) {
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getReceiverId, userId);
        queryWrapper.orderByDesc(Message::getSentAt);
        return list(queryWrapper);
    }

    @Override
    public List<Message> getSentMessages(Long userId) {
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getSenderId, userId);
        queryWrapper.orderByDesc(Message::getSentAt);
        return list(queryWrapper);
    }

    @Override
    public List<Message> getConversation(Long userId, Long otherUserId) {
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        
        // 查询双方之间的消息
        queryWrapper.and(wrapper -> wrapper
                .and(w -> w.eq(Message::getSenderId, userId).eq(Message::getReceiverId, otherUserId))
                .or(w -> w.eq(Message::getSenderId, otherUserId).eq(Message::getReceiverId, userId)));
        
        // 按发送时间升序排序
        queryWrapper.orderByAsc(Message::getSentAt);
        
        return list(queryWrapper);
    }

    @Override
    public boolean markAsRead(Long messageId, Long userId) {
        // 获取消息
        Message message = getById(messageId);
        if (message == null) {
            return false;
        }
        
        // 验证接收者
        if (!message.getReceiverId().equals(userId)) {
            return false;
        }
        
        // 标记为已读
        message.setIsRead(Boolean.TRUE);
        return updateById(message);
    }

    @Override
    public int getUnreadCount(Long userId) {
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getReceiverId, userId);
        queryWrapper.eq(Message::getIsRead, Boolean.FALSE);
        return baseMapper.selectCount(queryWrapper).intValue();
    }
}