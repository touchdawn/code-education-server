package com.itheima.domain;

import lombok.Data;

import java.util.Date;

@Data
public class FileStorage {
    private Integer id;
    private String url;
    private Integer createId;
    private Integer deleteFlag;
    private Date createdAt;
}
