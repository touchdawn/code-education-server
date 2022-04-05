package com.itheima.domain;

import lombok.Data;

import java.util.Date;

@Data
public class StudentHomeworkAnswer {
    private Integer id;
    private Integer homeworkRel;
    private Integer creatorId;
    private String answer;
    private Date createAt;
    private Integer deleteFlag;
    private Integer correctFlag;
    private String type;
}
