package com.itheima.controller;

import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class EmailController {
@Autowired
private JavaMailSender javaMailSender;

    private Map<String, Object> resultMap = new HashMap<>();
    @PostMapping("/sendEmail")
    public ApiResult sendEmail(@RequestBody User user){
        SimpleMailMessage message = new SimpleMailMessage();
        String code = VerifyCode(6);
        message.setFrom("3282145600@qq.com");
        message.setTo(user.getEmail());
        message.setSubject("【教培APP】");// 标题
        message.setText("【教培APP】你的验证码为："+code+"，有效时间为5分钟(若不是本人操作，可忽略该条邮件)");// 内容
        try {
            javaMailSender.send(message);
            System.out.println("success");
            saveCode(code);
            return ApiResult.T(resultMap);
        }catch (MailSendException e){
            System.out.println("目标邮箱不存在");;
            return ApiResult.F("","目标邮箱不存在");
        } catch (Exception e) {
            System.out.println("文本邮件发送异常！");;
            return ApiResult.F("","文本邮件发送异常");
        }

    }

    //保存验证码和时间
    private void saveCode(String code){
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 5);
        String currentTime = sf.format(c.getTime());// 生成5分钟后时间，用户校验是否过期

//        String hash =  MD5Utils.code(code);//生成MD5值
        resultMap.put("code", code);
        resultMap.put("tamp", currentTime);
    }

    private String VerifyCode(int n){
        Random r = new Random();
        StringBuffer sb =new StringBuffer();
        for(int i = 0;i < n;i ++){
            int ran1 = r.nextInt(10);
            sb.append(String.valueOf(ran1));
        }
        return sb.toString();
    }
}
