package com.itheima.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.domain.SkillMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

@Mapper
public interface SkillMapDao extends BaseMapper<SkillMap> {

    @Select("select sm.SKILL_NAME as skillName,\n" +
            "       sm.RECOMMEND_REASON as recommendReason,\n" +
            "       li.ID as courseId,\n" +
            "       li.LESSON_NAME as courseName,\n" +
//            "       li.CREATE_AT as creatAt,\n" +
            "       li.CREATOR_ID as creatorId,\n" +
            "       DATE_FORMAT(li.CREATE_AT, '%m-%d') As \"createTime\"," +
            "       li.IMG_URL as courseUrl\n" +
            "from skill_map sm left join lesson_info li on sm.COURSE_REL = li.ID\n" +
            "where sm.PARENT_ID = #{parentId} and sm.DELETE_FLAG = 1 " +
            "and li.STATUS = 1 order by sm.DISPLAY_ORDER")
    List<Map> getSkillMapByParentId(Integer parentId);


    @Select("select * from skill_map where PARENT_ID = -1 and DELETE_FLAG = 1")
    List<Map> getSkillMapTitle();

}
