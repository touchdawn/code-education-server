package com.itheima.domain;


import lombok.Data;

import java.util.Date;

@Data
public class CourseCommentVote {
    private Integer id;
    private Integer commentId;
    private Integer voterId;
    private Date createAt;
    private Integer deleteFlag;
    private Integer courseId;
}
