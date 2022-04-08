package com.itheima.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.domain.StudentHomeworkAnswer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentHomeworkAnswerDao extends BaseMapper<StudentHomeworkAnswer> {

    @Select("select * from student_homework_answer " +
            "where HOMEWORK_REL = #{hwId} and CREATOR_ID = #{userId}")
    StudentHomeworkAnswer getByHwIdAndUserId(Integer hwId, Integer userId);

}
