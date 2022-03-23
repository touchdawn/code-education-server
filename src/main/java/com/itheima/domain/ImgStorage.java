package com.itheima.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ImgStorage {
    private Integer id;
    private String imgUrl;
    private Integer deleteFlag;
    private Date createAt;
}
