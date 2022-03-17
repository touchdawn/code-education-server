package com.itheima.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTestCase {

    @Autowired
    private UserService userService;

    @Test
    void testGetById(){
        System.out.println(userService.getById(1));
    }

    @Test
    void testGetPage(){
        //IPage page = new Page(2,2);
        IPage<User> page = userService.getPage(2, 2);
        System.out.println(page.getSize());
        System.out.println(page.getCurrent());
        System.out.println(page.getTotal());
        System.out.println(page.getPages());
        System.out.println(page.getRecords());
    }

}
