package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.UserMessage;

import java.util.Map;

public interface UserMessageService extends IService<UserMessage> {
    Integer addMessage(Map<String, String> map);

    ApiResult setIsRead(Integer messageId);

    ApiResult getMyMessage(Integer userId);

    ApiResult deleteMessage(Integer messageId);

    ApiResult postChangeMessage(Map<String, String> map);

    ApiResult getMessageById(Integer messageId);

    ApiResult getMyMessageByPage(Integer userId, Integer current, Integer size);
}
