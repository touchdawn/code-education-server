package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.service.IUserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    public IPage<User> getPage(int currentPage, int pageSize, User user) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.like(Strings.isNotEmpty(user.getType().toString()),User::getType,user.getType());
        lqw.like(Strings.isNotEmpty(user.getName()),User::getName,user.getName());


        IPage page = new Page(currentPage,pageSize);
        userDao.selectPage(page,lqw);
        return page;
    }

    @Override
    public Boolean checkPassword(String phone, String password) {
        //LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        //LambdaQueryWrapper<User> queryWrapper1 = lqw.eq(User::getName, name);
        //LambdaQueryWrapper<User> queryWrapper = lqw.eq(User::getName, name).eq(User::getPassword, password);
        //return queryWrapper != null;
        boolean equals = Objects.equals(userDao.getPassword(phone), password);
        return equals;
    }


    /**
     *@param phone
     *@return 如果用户存在返回true
     */
    @Override
    public Boolean checkUserExist(String phone) {
        return userDao.checkUserPhoneExistOrNot(phone) != null;
    }


    @Override
    public Boolean checkEmailResult(String email) {
        return userDao.checkUserEmailExistOrNot(email) != null;
    }

    @Override
    public User findUserByPhone(String phone) {
        return userDao.findByPhone(phone);
    }
}
