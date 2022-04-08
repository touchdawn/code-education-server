package com.itheima.domain;

import lombok.Data;

import java.util.Date;

@Data
public class UserLocalData {
    private Integer id;
    private Integer userId;
    private String userData;
    private Date uploadAt;
    private Integer latestCourseId;
    private Integer deleteFlag;
}
