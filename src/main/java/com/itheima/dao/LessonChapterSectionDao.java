package com.itheima.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.domain.LessonChapterSection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper

public interface LessonChapterSectionDao extends BaseMapper<LessonChapterSection> {
    @Select("select * from lesson_chapter_section" +
            " where LESSON_REL = #{courseId} and PARENT_ID = -1 and DELETE_FLAG = 1 order by DISPLAY_ORDER asc ")
    List<Map> getCourseChapterInfo(Integer courseId);

//    @Select("select * from lesson_chapter_section lcs left join file_storage fs on fs.SECTION_REL = lcs.ID where lcs.PARENT_ID = #{parentId} and lcs.DELETE_FLAG = 0")
    @Select("select * from lesson_chapter_section lcs left join file_storage fs on lcs.FILE_REL = fs.ID\n " +
            "where lcs.PARENT_ID = #{parentId} and lcs.DELETE_FLAG = 1 order by lcs.DISPLAY_ORDER asc")
    List<Map> getChapterChildInfoAll(Integer parentId);

    @Select("select * from lesson_chapter_section lcs left join file_storage fs on lcs.FILE_REL = fs.ID\n " +
            "where lcs.PARENT_ID = #{parentId} and lcs.STATUS = 1 and lcs.DELETE_FLAG = 1 order by lcs.DISPLAY_ORDER asc")
    List<Map> getChapterChildInfo(Integer parentId);

//    @Select("select * from lesson_chapter_section where PARENT_ID = #{id} and DELETE_FLAG = 1")
    @Select("UPDATE lesson_chapter_section\n" +
            "SET DELETE_FLAG = 0\n" +
            "WHERE PARENT_ID =#{parentId} and DELETE_FLAG = 1")
    List<Map> deleteChild(Integer parentId);

    @Select("select *\n" +
            "from lesson_chapter_section lcs left join lesson_info li on li.ID = lcs.LESSON_REL\n" +
            "left join file_storage fs on lcs.FILE_REL = fs.ID\n" +
            "left join user on user.ID = li.CREATOR_ID\n" +
            "where lcs.STATUS = 0 and PARENT_ID != -1 order by PARENT_ID desc , lcs.CREATE_AT desc ")
    List<Map> getAuditingSection();


//    获取章节数量
    @Select("select count(1) from lesson_chapter_section where STATUS = #{status} and PARENT_ID != -1")
    Integer getSectionCountByStatus(Integer status);

    @Select("select *\n" +
            "from lesson_chapter_section lcs left join lesson_info li on li.ID = lcs.LESSON_REL\n" +
            "left join file_storage fs on lcs.FILE_REL = fs.ID\n" +
            "left join user on user.ID = li.CREATOR_ID\n" +
            "where lcs.STATUS = 0 and PARENT_ID != -1 order by PARENT_ID desc , lcs.CREATE_AT desc " +
            "limit #{start},#{size}")
    List<Map> getAuditingSectionByPage(Integer start, Integer size);
}
