package com.itheima.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.controller.utils.ApiResult;
import com.itheima.dao.TeacherApplicationDao;
import com.itheima.dao.UserDao;
import com.itheima.domain.TeacherApplication;
import com.itheima.domain.User;
import com.itheima.service.TeacherApplicationService;
import com.itheima.service.UserMessageService;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeacherApplicationServiceImpl extends ServiceImpl<TeacherApplicationDao,TeacherApplication> implements TeacherApplicationService {

    @Autowired
    TeacherApplicationDao teacherApplicationDao;

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    @Autowired
    UserMessageService userMessageService;

    @Override
    public ApiResult addNewApplication(Map<String, String> map) {

        Integer applicantId = Integer.parseInt(map.get("applicantId"));
        String fileUrl = map.get("fileUrl");
        String applyReason = map.get("applyReason");
        TeacherApplication teacherApplication = new TeacherApplication();
        teacherApplication.setApplicantId(applicantId);
        teacherApplication.setApplyReason(applyReason);
        teacherApplication.setFileUrl(fileUrl);
        teacherApplication.setCreateAt(new Date());
        teacherApplication.setApplyStatus(0);
        save(teacherApplication);
        return ApiResult.T();
    }

    @Override
    public ApiResult getTeacherApplication() {
        List<Map> teacherApplyListByStatus = teacherApplicationDao.getTeacherApplyListByStatus(0);
        return ApiResult.T(teacherApplyListByStatus);
    }

    @Override
    public ApiResult approveTeacherApplication(Integer applyId) {
        TeacherApplication teacherApplication = teacherApplicationDao.selectById(applyId);
        teacherApplication.setApplyStatus(1);
        teacherApplicationDao.updateById(teacherApplication);

        User user = userDao.selectById(teacherApplication.getApplicantId());
        user.setType(1);
        userService.update(user);

        String messageContent = "尊敬的用户" + user.getNickName() + "：\n"+
                "      恭喜你通过了教师的审核，感谢彼此的相遇，让我们一起为教育事业增光添彩吧！\n"+
                "      在\"我的\"界面中可以申请开课，审核通过后就可以在\"我的课程\"中上传视频/教学啦！";
        Map<String,String> map = new HashMap<>();
        map.put("senderId","1");
        map.put("receiverId",teacherApplication.getApplicantId().toString());
        map.put("title","教师申请通过通知");
        map.put("content",messageContent);
        map.put("parentMessageId","-1");
        userMessageService.addMessage(map);
        return ApiResult.T();
    }

    @Override
    public ApiResult disApproveTeacherApplication(Integer applyId) {
        TeacherApplication teacherApplication = teacherApplicationDao.selectById(applyId);
        teacherApplication.setApplyStatus(9);
        teacherApplicationDao.updateById(teacherApplication);
        User user = userDao.selectById(teacherApplication.getApplicantId());
        user.setType(0);
        userService.update(user);

        String messageContent = "尊敬的用户" + user.getNickName() + "：\n"+
                                "      很抱歉，您提交的教师申请并未通过，您可以稍后继续提交申请。\n" +
                                "再次感谢您对本软件的抬爱与对教育事业的支持！";
        Map<String,String> map = new HashMap<>();
        map.put("senderId","1");
        map.put("receiverId",teacherApplication.getApplicantId().toString());
        map.put("title","关于教师申请的通知");
        map.put("content",messageContent);
        map.put("parentMessageId","-1");
        userMessageService.addMessage(map);
        return ApiResult.T();
    }
}
