package com.itheima.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.domain.TeacherApplication;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TeacherApplicationDao extends BaseMapper<TeacherApplication> {


    @Select("select user.NAME as username,\n" +
            "       user.AVATAR as applierAvatar,\n" +
            "       ta.ID as id,\n" +
            "       ta.APPLICANT_ID as applicantId,\n" +
            "       ta.CREATE_AT as createAt,\n" +
            "       ta.APPLY_REASON as applyReason,\n" +
            "       ta.APPLY_STATUS as applyStatus,\n" +
            "       ta.FILE_URL as fileUrl\n" +
            "from teacher_application ta left join user on ta.APPLICANT_ID = user.ID\n" +
            "and ta.DELETE_FLAG = 1 and user.STATUS = 1\n" +
            "where APPLY_STATUS = #{status}\n" +
            "order by CREATE_AT desc")
    List<Map> getTeacherApplyListByStatus(Integer status);
}
