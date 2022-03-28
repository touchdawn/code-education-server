package com.itheima.domain;

import lombok.Data;

import java.util.Date;

@Data
public class UserComment {
    private Integer id;
    private String content;
    private Integer creatorId;
    private Integer courseId;
    private Integer parentId;
    private Date createAt;
    private Integer votes;
    private Integer deleteFlag;
}
