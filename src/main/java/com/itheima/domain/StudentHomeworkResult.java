package com.itheima.domain;

import lombok.Data;

import java.util.Date;

@Data
public class StudentHomeworkResult {
    private Integer id;
    private Integer homeworkRel;
    private Integer indexRel;
    private Integer incorrectNumber;
    private Integer correctNumber;
    private String allAnswer;
    private Date createAt;
    private Integer deleteFlag;
}
