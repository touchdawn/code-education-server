package com.itheima.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserDaoTestCase {
    @Autowired
    private UserDao userDao;
    @Test
    void testGetById(){
        System.out.println(userDao.selectById(2));
    }

    @Test
    void testSave(){
        User user = new User();
        user.setName("测试3姓名");
        user.setNickName("测试3昵称");
        user.setEmail("333@33.33");
        user.setPhone("33333333333");
        user.setPassword("111111");
        userDao.insert(user);
    }

    @Test
    void testUpdate(){
        User user = new User();
        user.setId(2);
        user.setName("测试2姓名");
        user.setNickName("测试2昵称");
        user.setEmail("2222@33.33");
        user.setPhone("2222222");
        user.setPassword("111111");
        userDao.updateById(user);
    }

    @Test
    void testDelete(){
        userDao.deleteById(3);
    }

    @Test
    void testGetAll(){
        List<User> users = userDao.selectList(null);
        //System.out.println(users);
    }

    @Test
    void testGetPage(){
        IPage page = new Page(1,2);
        userDao.selectPage(page,null);
        System.out.println(page.getSize());
        System.out.println(page.getCurrent());
        System.out.println(page.getTotal());
        System.out.println(page.getPages());
        System.out.println(page.getRecords());
    }

    @Test
    void testGetBy(){
        QueryWrapper<User>qw = new QueryWrapper<>();
        qw.like("name","1");
        userDao.selectList(qw);
    }

    @Test
    void testGetBy2(){
        String name = "1";
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        //if (name != null){
        //
        //}
        lqw.like(name != null,User::getName,name);
        userDao.selectList(lqw);
    }
}
