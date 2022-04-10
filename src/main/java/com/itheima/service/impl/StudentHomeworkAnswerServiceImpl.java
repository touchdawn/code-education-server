package com.itheima.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.controller.utils.ApiResult;
import com.itheima.dao.CourseHomeworkDao;
import com.itheima.dao.StudentHomeworkAnswerDao;
import com.itheima.dao.StudentHomeworkResultDao;
import com.itheima.domain.CourseHomework;
import com.itheima.domain.StudentHomeworkAnswer;
import com.itheima.domain.StudentHomeworkResult;
import com.itheima.service.StudentHomeworkAnswerService;
import com.itheima.service.StudentHomeworkResultService;
import com.itheima.util.RedisUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StudentHomeworkAnswerServiceImpl extends ServiceImpl<StudentHomeworkAnswerDao, StudentHomeworkAnswer> implements StudentHomeworkAnswerService {

    @Autowired
    CourseHomeworkDao courseHomeworkDao;

    @Autowired
    StudentHomeworkResultDao studentHomeworkResultDao;

    @Autowired
    StudentHomeworkAnswerDao studentHomeworkAnswerDao;

    @Autowired
    StudentHomeworkResultService studentHomeworkResultService;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public ApiResult addStudentHomeworkAnswer(StudentHomeworkAnswer studentHomeworkAnswer) {
        studentHomeworkAnswer.setCreateAt(new Date());
        studentHomeworkAnswer.setDeleteFlag(1);
        save(studentHomeworkAnswer);

        Integer returnId = studentHomeworkAnswer.getHomeworkRel();

        CourseHomework courseHomework = courseHomeworkDao.selectById(returnId);
        JSONArray jsonArray = JSONArray.fromObject(courseHomework.getAnswer());// 正确答案

        JSONArray studentArray = JSONArray.fromObject(studentHomeworkAnswer.getAnswer());

        //准备刷新统计数据
        List<StudentHomeworkResult> homeworkResult = studentHomeworkResultDao.getHomeworkResult(returnId);

        AtomicInteger count= new AtomicInteger();
        count.set(0);
        //统计表更新
        studentArray.forEach(ans->{
            StudentHomeworkResult studentHomeworkResult = homeworkResult.get(count.get());
            Integer correctNumber =studentHomeworkResult.getCorrectNumber();
            Integer incorrectNumber = studentHomeworkResult.getIncorrectNumber();
//            if (ans == jsonArray.get(count.get()).toString()){ //答对了
            if (Objects.equals(ans, jsonArray.get(count.get()).toString())){ //答对了
                studentHomeworkResult.setCorrectNumber(correctNumber + 1);
            } else {
                studentHomeworkResult.setIncorrectNumber(incorrectNumber + 1);
            }


            String allAnswer = studentHomeworkResult.getAllAnswer();
            //判断ABCD
            if (Objects.equals(ans.toString(), "A")){
                int i1 = Integer.parseInt(JSONArray.fromObject(allAnswer).get(0).toString()) + 1;
                String res = allAnswer.substring(0,2)+ i1 + allAnswer.substring(3,17);
                studentHomeworkResult.setAllAnswer(res);
            } else if (Objects.equals(ans.toString(), "B")){
                int i2 = Integer.parseInt(JSONArray.fromObject(allAnswer).get(1).toString()) + 1;
                String res = allAnswer.substring(0,6) + i2 + allAnswer.substring(7,17);
                studentHomeworkResult.setAllAnswer(res);
            } else if (Objects.equals(ans.toString(), "C")){
                int i3 = Integer.parseInt(JSONArray.fromObject(allAnswer).get(2).toString()) + 1;
                String res = allAnswer.substring(0,10) + i3 + allAnswer.substring(11,17);
                studentHomeworkResult.setAllAnswer(res);
            } else if (Objects.equals(ans.toString(), "D")){
                int i4 = Integer.parseInt(JSONArray.fromObject(allAnswer).get(3).toString()) + 1;
                String res = allAnswer.substring(0,14) + i4 + allAnswer.substring(15,17);
                studentHomeworkResult.setAllAnswer(res);
            }
            //count ++
            count.set(count.get() + 1);
            studentHomeworkResultService.updateById(studentHomeworkResult);
        });

        return ApiResult.T();
    }

    @Override
    public ApiResult setStudentDone(Integer homeworkId, Integer studentId) {
//        HomeworkDoneDomain homeworkDoneDomain = new HomeworkDoneDomain(homeworkId,studentId);
//        redisUtil.sSet(homeworkId.toString(),studentId);
//        System.out.println(redisUtil.get(homeworkId.toString()));
//        return ApiResult.T(redisUtil.sHasKey(homeworkId.toString(),studentId));
//        return ApiResult.T(redisUtil.get(homeworkId.toString()));
        return null;
    }

    @Override
    public ApiResult getStudentAnswer(Integer hwId, Integer userId) {
        StudentHomeworkAnswer byHwIdAndUserId = studentHomeworkAnswerDao.getByHwIdAndUserId(hwId, userId);
        JSONArray jsonArray = JSONArray.fromObject(byHwIdAndUserId.getAnswer());
        return ApiResult.T(jsonArray);
    }
}
