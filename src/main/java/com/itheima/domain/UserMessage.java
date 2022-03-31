package com.itheima.domain;

import lombok.Data;

import java.util.Date;

@Data
public class UserMessage {
    private Integer id;
    private Integer senderId;
    private Integer receiverId;
    private Date createAt;
    private Date readAt;
    private String title;
    private String content;
    private Integer deleteFlag;
    private Integer receiverDeleteFlag;
    private Integer isRead;
    private Integer parentId;
}
