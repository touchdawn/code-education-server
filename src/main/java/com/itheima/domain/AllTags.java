package com.itheima.domain;

import lombok.Data;

import java.util.Date;

@Data
public class AllTags {
    Integer id;
    String tagName;
    Integer parentId;
    Date createdAt;
}
