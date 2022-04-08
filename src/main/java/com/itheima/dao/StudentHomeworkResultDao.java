package com.itheima.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.domain.StudentHomeworkResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentHomeworkResultDao extends BaseMapper<StudentHomeworkResult> {

    @Select("select * from student_homework_result where HOMEWORK_REL = #{homeworkId} order by INDEX_REL")
    List<StudentHomeworkResult> getHomeworkResult(Integer homeworkId);

    @Select("select shr.ID as studentHwResultId,\n" +
            "       ch.ID as homeworkId,\n" +
            "       shr.INDEX_REL as indexRel,\n" +
            "       shr.CORRECT_NUMBER as correctNumber,\n" +
            "       shr.INCORRECT_NUMBER as incorrectNumber,\n" +
            "       ALL_ANSWER as allAnswer,\n" +
            "       QUESTION as question,\n" +
            "       ANSWER as answer,\n" +
            "       HOMEWORK_NAME as homeworkName\n" +
            "from student_homework_result shr\n" +
            "    left join course_homework ch on shr.HOMEWORK_REL = ch.ID\n" +
            "where HOMEWORK_REL = #{homeworkId} order by INDEX_REL")
    List<Map> teacherGetHomeworkResult(Integer homeworkId);
}
