package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.controller.utils.ApiResult;
import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.service.IUserService;
import com.itheima.util.JwtUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.lang.Integer.parseInt;

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
    public Boolean checkPassword(String email, String password) {
        //LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        //LambdaQueryWrapper<User> queryWrapper1 = lqw.eq(User::getName, name);
        //LambdaQueryWrapper<User> queryWrapper = lqw.eq(User::getName, name).eq(User::getPassword, password);
        //return queryWrapper != null;
        boolean equals = Objects.equals(userDao.getPassword(email), password);
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

    @Override
    public User findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public ApiResult doRegister(Map<String, String> map) {

        System.out.println(map);
        Map<String, Object> resultMap = new HashMap<>();
        String phone = map.get("phone");
        String name = map.get("name");
        String email = map.get("email");
        String verifyCode = map.get("verifyCode");
        String password = map.get("password");
        String codeFromServer = map.get("verifyCode");
        String tampFromServer = map.get("tampFromServer");
//        Boolean checkEmailResult = checkEmailResult(email);
        Boolean checkEmailResult = false;

        Boolean checkPhoneResult = checkUserExist(phone);

//        Boolean checkPhoneResult = userService.checkUserExist(user.getPhone());
//        Boolean checkEmailResult = userService.checkEmailResult(user.getEmail());
        Boolean checkVerifyCode = (Objects.equals(verifyCode, codeFromServer));
//        Boolean checkTime = (Long.parseLong(tampFromServer)-System.currentTimeMillis())<= 3000000;
        if (!checkVerifyCode){
            return  ApiResult.F("","验证码不正确");
        } else if (!checkPhoneResult && !checkEmailResult){ //如果用户不存在且手机未注册
            User user = new User();
            user.setToken("");
            user.setEmail(email);
            user.setName(name);
            user.setNickName(name);
            user.setPhone(phone);
            user.setPassword(password);
            user.setCreatedAt(new Date());
            user.setUpdatedAt(new Date());
            user.setAvatar("default%20avatar.png");
            user.setType(0);
            save(user);
            //添加token
            user.setToken(JwtUtil.createToken(user));
            System.out.println(user);
            return ApiResult.T("registerSuccess",user);
        } else {
            return ApiResult.F();
        }
    }

    @Override
    public ApiResult getByUserId(Integer id) {
        User user = userDao.selectById(id);
        if (user != null){
            if (user.getAvatar() == null) {
                user.setAvatar("defaultAvatar2.png");
            }
            return  ApiResult.T(user);
        } else {
            return  ApiResult.F("","用户不存在");
        }
    }

    @Override
    public ApiResult updateUserById(User user) {
        Integer userId = user.getId();
        User user1 = userDao.selectById(userId);
        user1.setAvatar(user.getAvatar());
        user1.setNickName(user.getNickName());
        user1.setPhone(user.getPhone());
        user1.setUpdatedAt(new Date());
        int i = userDao.updateById(user1);
        if (i>0){
            return ApiResult.T("成功");
        }
        return ApiResult.F("","更新失败");
    }

    @Override
    public ApiResult changePassword(Map map) {
        String oldPwd = map.get("oldPassword").toString();
        String newPwd = map.get("newPassword").toString();
        Integer id = parseInt((map.get("id").toString()));
        String pwdInSql = userDao.getPasswordById(id);
        if (Objects.equals(pwdInSql, oldPwd)){
            User user = userDao.selectById(id);
            user.setPassword(newPwd);
            userDao.updateById(user);
            return ApiResult.T();
        } else {
            return ApiResult.F("","密码不正确");
        }
    }

    @Override
    public ApiResult getAllByPage(Map map) {
        Map<String,Integer> pageInfo = (Map) map.get("pageInfo");
//        int current = parseInt((String) pageInfo.get("current"));
//        int size = parseInt((String) pageInfo.get("size"));
        int current = pageInfo.get("current");
        int size = pageInfo.get("size");
        Map<String,String> searchMap = (Map) map.get("searchMap");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (searchMap != null){
            String name = searchMap.get("userName");
            String phone = searchMap.get("phone");
            String email = searchMap.get("email");
            if (name != null && !name.equals("")){
                queryWrapper.like("name",name);
            }
            if (phone != null && !phone.equals("")){
                queryWrapper.like("phone",phone);
            }
            if (email != null && !email.equals("")){
                queryWrapper.like("email",email);
            }

        }
//        IPage page = new Page(current,size);
//        IPage iPage = userDao.selectPage(page, queryWrapper);
        IPage page2 = userDao.selectPage(new Page(current,size),queryWrapper);

        return ApiResult.T(page2);
    }
}
