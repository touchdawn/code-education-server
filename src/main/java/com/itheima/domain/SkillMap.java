package com.itheima.domain;

import lombok.Data;

import java.util.Date;

@Data
public class SkillMap {
    private Integer id;
    private Integer parentId;
    private String skillName;
    private Integer displayOrder;
    private Date createAt;
    private Integer courseRel;
    private Integer deleteFlag;
    private String recommendReason;
}
