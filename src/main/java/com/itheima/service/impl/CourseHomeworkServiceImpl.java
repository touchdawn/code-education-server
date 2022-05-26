package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.controller.utils.ApiResult;
import com.itheima.dao.CourseHomeworkDao;
import com.itheima.dao.StudentHomeworkAnswerDao;
import com.itheima.dao.StudentHomeworkResultDao;
import com.itheima.domain.CourseHomework;
import com.itheima.domain.StudentHomeworkAnswer;
import com.itheima.domain.StudentHomeworkResult;
import com.itheima.service.CourseHomeworkService;
import com.itheima.service.StudentHomeworkResultService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CourseHomeworkServiceImpl extends ServiceImpl<CourseHomeworkDao, CourseHomework> implements CourseHomeworkService {

    @Autowired
    CourseHomeworkDao courseHomeworkDao;

    @Autowired
    StudentHomeworkAnswerDao studentHomeworkAnswerDao;

    @Autowired
    StudentHomeworkResultDao studentHomeworkResultDao;

    @Autowired
    StudentHomeworkResultService studentHomeworkResultService;

    @Override
    public ApiResult addHomework(CourseHomework courseHomework) {
        System.out.println("教师开始添加作业");
        Date date = new Date();
        courseHomework.setCreateAt(date);
        courseHomework.setDeleteFlag(1);
        Integer check = courseHomeworkDao.getId(courseHomework.getHomeworkName(), courseHomework.getCourseRel(), courseHomework.getCreatorId());
        if (check == null){
            save(courseHomework);
        } else {
            return ApiResult.F("","该作业已存在");
        }
        System.out.println("作业添加成功");
        //得到添加后的ID

        Integer homeworkId = courseHomeworkDao.getId(courseHomework.getHomeworkName(), courseHomework.getCourseRel(), courseHomework.getCreatorId());

        JSONArray jsonArray = JSONArray.fromObject(courseHomework.getAnswer());
        AtomicInteger count= new AtomicInteger();
        count.set(0);
        jsonArray.forEach(array->{
            StudentHomeworkResult studentHomeworkResult = new StudentHomeworkResult();
            studentHomeworkResult.setCreateAt(new Date());
            studentHomeworkResult.setDeleteFlag(1);
            studentHomeworkResult.setIndexRel(count.get());
            studentHomeworkResult.setHomeworkRel(homeworkId);
            studentHomeworkResult.setCorrectNumber(0);
            studentHomeworkResult.setIncorrectNumber(0);
            studentHomeworkResult.setAllAnswer("[\"0\",\"0\",\"0\",\"0\"]");
            studentHomeworkResultService.save(studentHomeworkResult);
            count.set(count.get() + 1);
        });
        System.out.println("添加初始统计数据成功");
        return ApiResult.T();
    }

    @Override
    public ApiResult getHomeworkByHwId(Integer hwId) {
        CourseHomework courseHomework = courseHomeworkDao.selectById(hwId);
        return ApiResult.T(courseHomework);
    }

    @Override
    public ApiResult getHomeworkByCourseId(Integer courseId, Integer userId) {
        List<Map> byCourseId = courseHomeworkDao.getByCourseId(courseId);
        byCourseId.forEach(map -> {
//            String id = map.get("id");
            int homeworkId =(Integer) map.get("id");
            StudentHomeworkAnswer byHwIdAndUserId = studentHomeworkAnswerDao.getByHwIdAndUserId(homeworkId, userId);
            if (byHwIdAndUserId != null){
                map.put("doneFlag","1");
                map.put("doneTime", byHwIdAndUserId.getCreateAt().getTime());
            } else {
                map.put("doneFlag","0");
            }
        });
        return ApiResult.T(byCourseId);
    }

    @Override
    public ApiResult checkAuthority(Integer courseId, Integer userId) {
        Integer creatorIdByCourseId = courseHomeworkDao.getCreatorIdByCourseId(courseId);

        if (Objects.equals(creatorIdByCourseId, userId)){
            return ApiResult.T("creator");
        } else {
            return ApiResult.T("readOnly");
        }
    }


}
