package com.itheima.domain;

import lombok.Data;

import java.util.Date;

@Data
public class CourseHomework {
    private Integer id;
    private Integer courseRel;
    private Integer creatorId;
    private String homeworkName;
    private String question;
    private String answer;
    private Date createAt;
    private Integer deleteFlag;
}
