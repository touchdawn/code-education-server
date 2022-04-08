package com.itheima.controller;


import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.UserLocalData;
import com.itheima.service.UserLocalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userData")
public class UserLocalDataController {

    @Autowired
    UserLocalDataService userLocalDataService;

    @GetMapping("/getUserData/{userId}")
    public ApiResult getUserData(@PathVariable Integer userId){
        return userLocalDataService.getUserData(userId);
    }

    @PostMapping("/addUserData")
    public ApiResult addUserData(@RequestBody UserLocalData userLocalData){
        return userLocalDataService.addUserData(userLocalData);
    }
}
