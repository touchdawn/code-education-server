package com.itheima.controller;


import com.itheima.controller.utils.ApiResult;
import com.itheima.service.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/message")
public class UserMessageController {
    @Autowired
    private UserMessageService userMessageService;

    @PostMapping("/addMessage")
    public ApiResult addMessage(@RequestBody Map<String, String> map){
        return ApiResult.T( userMessageService.addMessage(map));
    }

    @GetMapping("/getMessageById/{messageId}")
    public ApiResult getMessageById(@PathVariable Integer messageId) {
        return userMessageService.getMessageById(messageId);
    }

    @GetMapping("/getMyMessage/{userId}")
    public ApiResult getMyMessage(@PathVariable Integer userId) {
        return userMessageService.getMyMessage(userId);
    }

    @GetMapping("/getMyMessageByPage/{userId}")
    public ApiResult getMyMessageByPage(@PathVariable Integer userId,@RequestParam Integer current,@RequestParam Integer size) {
        return userMessageService.getMyMessageByPage(userId,current,size);
    }

    @GetMapping("/isRead/{messageId}")
    public ApiResult isRead(@PathVariable Integer messageId) {
        return userMessageService.setIsRead(messageId);
    }

    @GetMapping("/deleteMessage/{messageId}")
    public ApiResult deleteMessage(@PathVariable Integer messageId) {
        return userMessageService.deleteMessage(messageId);
    }

    @PostMapping("/postChangeMessage")
    public ApiResult postChangeMessage(@RequestBody Map<String, String> map) {
        return userMessageService.postChangeMessage(map);
    }
}
