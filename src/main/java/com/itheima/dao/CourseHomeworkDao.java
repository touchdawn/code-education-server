package com.itheima.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.domain.CourseHomework;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface CourseHomeworkDao extends BaseMapper<CourseHomework> {


    @Select("select id from course_homework where HOMEWORK_NAME = #{homeworkName} and COURSE_REL = #{courseRel} and CREATOR_ID = #{creatorId}")
    Integer getId(String homeworkName, Integer courseRel, Integer creatorId);


    @Select("select ID as id,\n" +
            "       HOMEWORK_NAME as homeworkName, \n" +
            "       DATE_FORMAT(CREATE_AT, '%m-%d %H:%i') As \"createAt\"" +
            "from course_homework\n" +
            "where COURSE_REL = #{courseId} and DELETE_FLAG = 1\n" +
            "order by CREATE_AT")
    List<Map> getByCourseId(Integer courseId);

    @Select("select distinct CREATOR_ID from lesson_info where ID = #{courseId}")
    Integer getCreatorIdByCourseId(Integer courseId);
}
