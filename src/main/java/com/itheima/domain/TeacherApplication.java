package com.itheima.domain;

import lombok.Data;

import java.util.Date;

@Data
public class TeacherApplication {
    private Integer id;
    private Integer applicantId;
    private String fileUrl;
    private Integer deleteFlag;
    private Date createAt;
    private String applyReason;
    private Integer applyStatus;
}
