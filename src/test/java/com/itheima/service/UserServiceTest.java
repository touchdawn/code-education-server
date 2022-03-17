package com.itheima.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private IUserService userService;

    @Test
    void testGetById(){
        System.out.println(userService.getById(1));
    }

    @Test
    void testGetPage(){
        //IPage page = new Page(2,2);
        IPage<User> page = new Page<User>(2,2);
        IPage<User> page1 = userService.page(page);
        System.out.println(page1.getSize());
        System.out.println(page1.getCurrent());
        System.out.println(page1.getTotal());
        System.out.println(page1.getPages());
        System.out.println(page1.getRecords());
    }

    @Test
    void testRegister(){
        Boolean aBoolean = userService.checkUserExist("11111111111");
        System.out.println(aBoolean);
    }

}
