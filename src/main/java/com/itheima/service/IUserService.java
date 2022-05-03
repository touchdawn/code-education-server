package com.itheima.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.User;
import java.util.Map;

public interface IUserService extends IService<User> {

    IPage<User> getPage(int currentPage, int pageSize, User user);

    /**
     * @param email,password
     * @return 根据邮箱密码登录
     */
    Boolean checkPassword(String email,String password);

    /**
     * @param phone
     * @return 如果用户存在返回true
     */
    Boolean checkUserExist(String phone);

    /**
     * @param email
     * @return 如果用户邮箱存在返回true
     */
    Boolean checkEmailResult(String email);

    /**
     * @param phone
     * @return user
     */
    User findUserByPhone(String phone);


    /**
     * @param email
     * @return user
     */
    User findUserByEmail(String email);

    ApiResult doRegister(Map<String, String> map);

    ApiResult getByUserId(Integer id);

    ApiResult updateUserById(User user);

    ApiResult changePassword(Map map);

    ApiResult getAllByPage(Map map);
}
