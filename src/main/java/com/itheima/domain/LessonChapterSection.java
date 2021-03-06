package com.itheima.domain;

import lombok.Data;

import java.util.Date;

@Data
public class LessonChapterSection {
    private Integer id;
    private Integer lessonRel;
//    private String chapterName;
//    private String sectionName;
    private String title;
    private Integer fileRel;
    private Integer parentId;
    private Integer displayOrder;
    private Integer deleteFlag;
    private Integer status;
    private String type;
    private Date createAt;
}
