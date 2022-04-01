package com.itheima.domain;

import lombok.Data;

import java.util.Date;

@Data
public class FileStorage {
    private Integer id;
    private String url;
    private Integer creatorId;
    private Integer deleteFlag;
    private Date createdAt;
    private String sourceName;
    private Integer lessonRel;
    private Integer sectionRel;
    private Integer displayOrder;
}
