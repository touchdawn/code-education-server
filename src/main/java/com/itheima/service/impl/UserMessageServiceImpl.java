package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.controller.utils.ApiResult;
import com.itheima.dao.UserMessageDao;
import com.itheima.domain.UserMessage;
import com.itheima.service.UserMessageService;
import com.itheima.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Service
public class UserMessageServiceImpl extends ServiceImpl<UserMessageDao, UserMessage> implements UserMessageService {

    @Autowired
    UserMessageDao userMessageDao;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public Integer addMessage(Map<String, String> map) {
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
        Date date = new Date();
        userMessage.setCreateAt(date);
        userMessage.setDeleteFlag(1);
        if (senderId == 1){
            userMessage.setIsRead(0);
        } else {
            userMessage.setIsRead(1);
        }
//        save(userMessage);
        userMessageDao.insert(userMessage);

        Integer idCallback = userMessage.getId();
        //如果发送者ID为1，redis中添加红点(先不实现了)
//        if (senderId == 1){
//            redisUtil.sAdd(idCallback + "IsRead" , senderId.toString());
//        }

        return idCallback;
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
        userMessageDao.updateById(userMessage);
        return ApiResult.T();
    }

    @Override
    public ApiResult postChangeMessage(Map<String, String> map) {
        Integer messageId = Integer.parseInt(map.get("messageId"));
        String method = map.get("method");
        if (Objects.equals(method, "deleteByReceiver")) {
            UserMessage userMessage = userMessageDao.selectById(messageId);
            userMessage.setReceiverDeleteFlag(0);
            userMessageDao.updateById(userMessage);
            return ApiResult.T("接收者删除成功");
        } else if (Objects.equals(method, "setRead")) {
            UserMessage userMessage = userMessageDao.selectById(messageId);
            userMessage.setIsRead(0);
            userMessageDao.updateById(userMessage);
            return ApiResult.T("设为已读成功");
        } else if (Objects.equals(method, "deleteBySender")) {
            UserMessage userMessage = userMessageDao.selectById(messageId);
            userMessage.setDeleteFlag(0);
            userMessageDao.updateById(userMessage);
            return ApiResult.T("发送者删除成功");
        } else if (Objects.equals(method, "deleteByAdmin")) {
            userMessageDao.deleteById(messageId);
            return ApiResult.T("物理删除成功");
        } else if (Objects.equals(method, "deleteNotification")) {
            UserMessage userMessage = userMessageDao.selectById(messageId);
            userMessage.setDeleteFlag(0);
            userMessageDao.updateById(userMessage);
            return ApiResult.T("系统通知删除成功");

        }
        return ApiResult.F("","删除失败");
    }

    @Override
    public ApiResult getMessageById(Integer messageId) {
        UserMessage userMessage = userMessageDao.selectById(messageId);
        if (userMessage.getIsRead() == 1){
            userMessage.setIsRead(0);
            userMessageDao.updateById(userMessage);
        }
        return ApiResult.T(userMessageDao.getMessageById(messageId));
    }

    @Override
    public ApiResult getMyMessageByPage(Integer userId, Integer current, Integer size) {
//        Page<Map> page = new Page<>(current, size);
//        page.setRecords(userMessageDao.getSentMessageByUserId(userId));
//        page.setTotal(userMessageDao.getSentMessageCountByUserId(userId));
//        return ApiResult.T(page);
        IPage<UserMessage> page = new Page<>(current, size);
        IPage<UserMessage> page1= page(page, new QueryWrapper<UserMessage>()
                .eq("sender_id", userId)
                .eq("delete_flag", 1)
                .eq("receiver_id", -1).orderByDesc("create_at"));
        return ApiResult.T(page1);
    }

    public void addMessageDot(Integer senderId, Integer receiverId, Integer messageId) {
//        redis中添加红点
//        redisUtil.sAdd(messageId,1);
    }
}
