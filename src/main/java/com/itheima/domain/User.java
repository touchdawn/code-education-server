package com.itheima.domain;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

//@Getter
//@Setter
@Data
public class User {
    private Integer id;
    private String name;
    private String nickName;
    private String phone;
    private String avatar;
    private String email;
    private String password;
    private Integer status;
    private Integer type;
    private Date createdAt;
    private Date updatedAt;
    private String token;
}