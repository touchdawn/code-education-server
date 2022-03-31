package com.itheima.domain;

import lombok.Data;

import java.util.Date;

@Data
public class UserFavourite {
    private Integer id;
    private Integer creatorId;
    private Integer courseId;
    private Date createAt;
}
