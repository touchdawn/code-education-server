package com.itheima.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.domain.UserMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMessageDao extends BaseMapper<UserMessage> {

    @Select("select user.ID as senderId,\n" +
            "       user.NAME as senderName,\n" +
            "       user.NICK_NAME as senderNickName,\n" +
            "       user.AVATAR as senderAvatar,\n" +
            "       user.TYPE as senderType,\n" +
            "       TITLE as title,\n" +
            "       CONTENT as content,\n" +
            "       IS_READ as isRead,\n" +
            "       READ_AT as readAt,\n" +
//            "       CREATE_AT as sendTime,\n" +
            "       DATE_FORMAT(CREATE_AT, '%m-%d %H:%i') As \"sendTime\"," +
//            "       DATE_FORMAT(CREATE_AT, '%m-%d %H:%i') As \"sendTime\"," +
            "       um.ID as messageId\n" +
            "from user_message um left join user on um.SENDER_ID = user.ID\n" +
            "where RECEIVER_ID = #{userId} and DELETE_FLAG = 1 and RECEIVER_DELETE_FLAG = 1 and user.STATUS = 1\n" +
            "order by IS_READ desc , CREATE_AT desc")
    List<Map> getMessageByUserId(Integer userId);


    @Select("select user.ID as senderId,\n" +
            "       user.NAME as senderName,\n" +
            "       user.NICK_NAME as senderNickName,\n" +
            "       user.AVATAR as senderAvatar,\n" +
            "       user.TYPE as senderType,\n" +
            "       TITLE as title,\n" +
            "       CONTENT as content,\n" +
            "       IS_READ as isRead,\n" +
            "       READ_AT as readAt,\n" +
//            "       CREATE_AT as sendTime,\n" +
            "       DATE_FORMAT(CREATE_AT, '%m-%d %H:%i') As \"sendTime\"," +
//            "       DATE_FORMAT(CREATE_AT, '%m-%d %H:%i') As \"sendTime\"," +
            "       um.ID as messageId\n" +
            "from user_message um left join user on um.SENDER_ID = user.ID \n" +
            "where um.ID = #{messageId}")
    Map getMessageById(Integer messageId);
}
