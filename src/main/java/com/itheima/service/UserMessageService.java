package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.UserMessage;

import java.util.Map;

public interface UserMessageService extends IService<UserMessage> {
    ApiResult addMessage(Map<String, String> map);

    ApiResult setIsRead(Integer messageId);

    ApiResult getMyMessage(Integer userId);

    ApiResult deleteMessage(Integer messageId);

    ApiResult getMessageById(Integer messageId);
}
