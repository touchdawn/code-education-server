package com.itheima.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itheima.controller.utils.ApiResult;
//import com.itheima.controller.utils.R;
import com.itheima.domain.User;
import com.itheima.service.IUserService;
import com.itheima.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/users")

public class UserController {
    @Autowired
    private IUserService userService;

    // private final String USERNAME = "admin";
    // private final String PASSWORD = "123123";
    @GetMapping("/login")
    public ApiResult login(User user){
        Boolean aBoolean = userService.checkPassword(user.getEmail(), user.getPassword());
        //if (USERNAME.equals(user.getName()) && PASSWORD.equals(user.getPassword())){
        if (aBoolean){
            User userByEmail = userService.findUserByEmail(user.getEmail());
            //添加token
            userByEmail.setToken(JwtUtil.createToken(userByEmail));
            //System.out.println(user);
            return ApiResult.T("registerSuccess",userByEmail);
        }
        return ApiResult.F("950", "密码错误");
    }

    @PostMapping("/register")
//    public ApiResult register(@RequestParam(value="phone",required=true)String phone,String email,String name){
        public ApiResult register(@RequestBody Map<String, String> map){
//        return ApiResult.F("951", "该用户/邮箱已存在，请直接登录");
        return userService.doRegister(map);
    }

    @GetMapping("/checkToken")
    public Boolean checkToken(HttpServletRequest request){
        String token = request.getHeader("token");
        return JwtUtil.checkToken(token);
    };

    @GetMapping("/getById/{id}")
    public ApiResult getByUserId(@PathVariable Integer id){
        return userService.getByUserId(id);
    };

    @PostMapping("/updateUserById")
    public ApiResult updateUserById(@RequestBody User user){
        return userService.updateUserById(user);
    };

    @PostMapping("/changePassword")
    public ApiResult changePassword(@RequestBody Map<String, String> map){
        return userService.changePassword(map);
    };

    @GetMapping
    public ApiResult getAll(){
        return ApiResult.T(userService.list());
    };

    @PostMapping ("/getAllByPage")
    public ApiResult getAllByPage(@RequestBody Map<String,String> map){
        return userService.getAllByPage(map);
    };

    @PostMapping
    public ApiResult save(@RequestBody User user){
        //R r = new R();
        //boolean flag = userService.save(user);
        //r.setFlag(flag);
        return ApiResult.T(userService.save(user));
    }

    @PutMapping
    public ApiResult update(@RequestBody User user){
        return ApiResult.T(userService.updateById(user));
    }

    @DeleteMapping("{id}")
    public ApiResult delete(@PathVariable Integer id){
        return ApiResult.T(userService.removeById(id));
    }

    @GetMapping("{id}")
    public ApiResult getById(@PathVariable Integer id){
        return ApiResult.T(userService.getById(id));
    }

    //@GetMapping("{currentPage}/{pageSize}")
    //public R getPage(@PathVariable int currentPage,@PathVariable int pageSize){
    //
    //    IPage<User> page = userService.getPage(currentPage,pageSize);
    //    if(currentPage > page.getPages()){
    //        page = userService.getPage((int)page.getPages(),pageSize);
    //    }
    //
    //    return new R(true,page);
    //}

    @GetMapping("{currentPage}/{pageSize}")
    public ApiResult getPage(@PathVariable int currentPage, @PathVariable int pageSize, User user){
        System.out.println(user);
        IPage<User> page = userService.getPage(currentPage,pageSize, user);
        if(currentPage > page.getPages()){
            page = userService.getPage((int)page.getPages(),pageSize,user);
        }
        return ApiResult.T(page);
    }
}
