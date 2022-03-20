package com.itheima.domain;

import lombok.Data;

import java.util.Date;

@Data
public class LessonInfo {
    private Integer id;
    private String lessonName;
    private Integer creatorId;
    private Integer status;
    private Date createdAt;
}
