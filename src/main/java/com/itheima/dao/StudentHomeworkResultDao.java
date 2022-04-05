package com.itheima.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.domain.StudentHomeworkResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentHomeworkResultDao extends BaseMapper<StudentHomeworkResult> {

    @Select("select * from student_homework_result where HOMEWORK_REL = #{homeworkId} order by INDEX_REL")
    List<StudentHomeworkResult> getHomeworkResult(Integer homeworkId);
}
