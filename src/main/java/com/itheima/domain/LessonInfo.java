package com.itheima.domain;

import lombok.Data;

import java.util.Date;

@Data
public class LessonInfo {
    private Integer id;
    private String lessonName;
    private Integer creatorId;
    private Integer status;
    private Date createAt;
    private Integer score;
    private Integer subscribeNum;
    private String courseIntroduction;
    private String imgUrl;
    private String courseType;
    private Integer fileRel;
}
