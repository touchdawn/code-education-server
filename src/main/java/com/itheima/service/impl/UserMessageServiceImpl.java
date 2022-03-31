package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.controller.utils.ApiResult;
import com.itheima.dao.UserMessageDao;
import com.itheima.domain.UserMessage;
import com.itheima.service.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class UserMessageServiceImpl extends ServiceImpl<UserMessageDao, UserMessage> implements UserMessageService {

    @Autowired
    UserMessageDao userMessageDao;

    @Override
    public ApiResult addMessage(Map<String, String> map) {
        Integer senderId =Integer.parseInt(map.get("senderId"));
        Integer receiverId =Integer.parseInt( map.get("receiverId"));
        String title = map.get("title");
        String content = map.get("content");
        Integer parentId = Integer.parseInt(map.get("parentMessageId"));
        UserMessage userMessage = new UserMessage();
        userMessage.setTitle(title);
        userMessage.setContent(content);
        userMessage.setParentId(parentId);
        userMessage.setSenderId(senderId);
        userMessage.setReceiverId(receiverId);
        userMessage.setCreateAt(new Date());
        userMessage.setDeleteFlag(1);
        userMessage.setIsRead(1);
        save(userMessage);
        return ApiResult.T();
    }

    @Override
    public ApiResult setIsRead(Integer messageId) {
        UserMessage userMessage = userMessageDao.selectById(messageId);
        userMessage.setIsRead(0);
        userMessage.setReadAt(new Date());
        return ApiResult.T();
    }

    @Override
    public ApiResult getMyMessage(Integer userId) {

        return ApiResult.T(userMessageDao.getMessageByUserId(userId));
    }

    @Override
    public ApiResult deleteMessage(Integer messageId) {
        UserMessage userMessage = userMessageDao.selectById(messageId);
        userMessage.setDeleteFlag(0);
        return ApiResult.T();
    }

    @Override
    public ApiResult getMessageById(Integer messageId) {
        return ApiResult.T(userMessageDao.getMessageById(messageId));
    }
}
