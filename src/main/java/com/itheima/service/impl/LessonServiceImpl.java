package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.controller.utils.ApiResult;
import com.itheima.dao.*;
import com.itheima.domain.*;
import com.itheima.service.FileStorageService;
import com.itheima.service.LessonChapterSectionService;
import com.itheima.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LessonServiceImpl extends ServiceImpl<LessonInfoDao, LessonInfo> implements LessonService {

    @Autowired
    private LessonInfoDao lessonInfoDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserCommentDao userCommentDao;

    @Autowired
    private FileStorageDao fileStorageDao;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private LessonChapterSectionService lessonChapterSectionService;

    @Autowired
    private LessonChapterSectionDao  lessonChapterSectionDao;

    @Autowired
    private UserFavouriteDao userFavouriteDao;

    @Override
    public Boolean testT(Integer testInt){
        return false;
    }

    @Override
    public ApiResult getAllLessons() {
        List<Map<String,String>> allLessons = lessonInfoDao.getAllLessons();
        return ApiResult.T(allLessons);
    }

    @Override
    public ApiResult getRandLessons(Integer lessonNumber) {
        List<Map<String,String>> randLessons = lessonInfoDao.getRandLessons(lessonNumber);
        return ApiResult.T(randLessons);
    }

    @Override
    public ApiResult getCourseInfo(Integer courseId, Integer userId, String type){
//        ImgStorage courseImgInfo = imgStorageDao.findByCourseId(courseId);
        Map<String,Object> map = new HashMap<>();
//        map.put("imgUrl",courseImgInfo.getImgUrl());
        LessonInfo courseMainInfo = lessonInfoDao.selectById(courseId);
        map.put("introduction",courseMainInfo.getCourseIntroduction());
        map.put("subscribeNum",courseMainInfo.getSubscribeNum().toString());
        map.put("score",courseMainInfo.getScore().toString());
        map.put("imgUrl",courseMainInfo.getImgUrl());
        map.put("courseType",courseMainInfo.getCourseType());
        map.put("courseName",courseMainInfo.getLessonName());
        User teacher = userDao.selectById(courseMainInfo.getCreatorId());
        map.put("teacherAvatar",teacher.getAvatar());
        map.put("teacherName",teacher.getNickName());
        map.put("teacherId",teacher.getId());


        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        tsStr = sdf.format(courseMainInfo.getCreateAt());

        map.put("createTime",tsStr);

        UserFavourite userFavourite = userFavouriteDao.findFavouriteRecordByUserIdAndCourseId(userId,courseId);
        if (userFavourite != null){
            map.put("isFavourite",true);
            map.put("favouriteId",userFavourite.getId());
        } else {
            map.put("isFavourite",false);
            map.put("favouriteId","-1");
        }

        //统计评论数量
        Map totalComments = userCommentDao.countTotalComments(courseId);
        map.put("totalComment",totalComments.get("count"));

        List<Object> chapterList = new ArrayList<>();

        //获取父信息
        List<Map> courseChapterSectionInfo = lessonChapterSectionDao.getCourseChapterInfo(courseId);
        courseChapterSectionInfo.forEach(obj ->{
            Integer parentId = (Integer) obj.get("ID");
            //获取子表
            if (Objects.equals(type, "all")) {
                List<Map> chapterChildInfo = lessonChapterSectionDao.getChapterChildInfoAll(parentId);
                obj.put("child",chapterChildInfo);
            } else {
                List<Map> chapterChildInfo = lessonChapterSectionDao.getChapterChildInfo(parentId);
                obj.put("child",chapterChildInfo);
            }
            chapterList.add(obj);
        });

        map.put("chapter",chapterList);
        map.put("favNum",userFavouriteDao.getFavNumberByCourseId(courseId));
        return ApiResult.T(map);
    }

    @Override
    public ApiResult addNewCourse(Map<String, String> map) {
        LessonInfo lessonInfo = new LessonInfo();
        lessonInfo.setLessonName(map.get("courseName"));
        lessonInfo.setCourseIntroduction(map.get("courseIntro"));
        lessonInfo.setStatus(0);
        lessonInfo.setScore(0);
        lessonInfo.setCreatorId(Integer.parseInt(map.get("creatorId")));
        lessonInfo.setImgUrl(map.get("courseCover"));
        lessonInfo.setCourseType(map.get("courseTag"));
        lessonInfo.setSubscribeNum(0);
        lessonInfo.setCreateAt(new Date());
        boolean save = save(lessonInfo);
        if (save){
            return ApiResult.T();
        } else {
            return ApiResult.F("","添加失败");
        }
    }

    @Override
    public ApiResult getLessonByTeacherId(Integer id) {
        List<LessonInfo> checkPassedList = lessonInfoDao.getAllByTeacherId(id, 1);
        List<LessonInfo> checkingList = lessonInfoDao.getAllByTeacherId(id, 0);
        Map<String,List> map = new HashMap<>();
        map.put("checkPassedList",checkPassedList);
        map.put("checkingList",checkingList);
        return ApiResult.T(map);
    }

    @Override
    public ApiResult addNewSectionVideo(Map<String, String> map) {
//        try {
            FileStorage fileStorage = new FileStorage();
            fileStorage.setCreatorId(Integer.parseInt(map.get("creatorId")));
            fileStorage.setUrl(map.get("lessonVideo"));
            fileStorage.setCreatedAt(new Date());
            fileStorageService.save(fileStorage);

            Integer fileId = fileStorageDao.findIdByUrl(map.get("lessonVideo"));
            LessonChapterSection lessonChapterSection = new LessonChapterSection();
            lessonChapterSection.setCreateAt(new Date());
            lessonChapterSection.setFileRel(fileId);
            lessonChapterSection.setTitle(map.get("sectionName"));
            lessonChapterSection.setParentId(Integer.parseInt(map.get("parentId")));
            lessonChapterSection.setLessonRel(Integer.parseInt(map.get("courseId")));
            String sectionName = map.get("lessonVideo");
        System.out.println(sectionName.substring(0,2));
            if ((sectionName.substring(0,2)).equals("MD")){
                lessonChapterSection.setType("MD");
            } else if ((sectionName.substring(0,5)).equals("video")){
                lessonChapterSection.setType("video");
            }
        System.out.println(lessonChapterSection);
        lessonChapterSectionService.save(lessonChapterSection);

//        } catch (Exception e) {
//            return ApiResult.F("", String.valueOf(e));
//        }
        return ApiResult.T();
    }

    @Override
    public ApiResult addNewChapter(Map<String, String> map) {
        LessonChapterSection lessonChapterSection = new LessonChapterSection();
        lessonChapterSection.setLessonRel(Integer.parseInt(map.get("courseId")));
        lessonChapterSection.setCreateAt(new Date());
        lessonChapterSection.setParentId(-1);
        lessonChapterSection.setTitle(map.get("chapterName"));
        lessonChapterSectionService.save(lessonChapterSection);
        return ApiResult.T();
    }

    @Override
    public ApiResult searchLesson(String lessonName) {
        QueryWrapper<LessonInfo> queryWrapper = new QueryWrapper<LessonInfo>();
//        queryWrapper.like("LESSON_NAME", "%node%");
        String val = "%" + lessonName + "%";
        queryWrapper.like("LESSON_NAME", val).eq("STATUS",1);
        List<LessonInfo> lessonList = lessonInfoDao.selectList(queryWrapper);
        return ApiResult.T(lessonList);
    }

    @Override
    public ApiResult deleteSection(Integer sectionId) {
        LessonChapterSection lessonChapterSection = lessonChapterSectionDao.selectById(sectionId);
        lessonChapterSection.setDeleteFlag(0);
        lessonChapterSectionDao.updateById(lessonChapterSection);
        if (lessonChapterSection.getParentId() != -1) {
            return ApiResult.T("删除section成功");
        } else {
            //删除的是chapter
            lessonChapterSectionDao.deleteChild(sectionId);
            return  ApiResult.T("删除chapter成功");
        }
    }

//    public void findByLike() {
//        QueryWrapper<LessonInfo> queryWrapper = new QueryWrapper<LessonInfo>();
//        queryWrapper.like("name", "%乔%");
//        List<LessonInfo> userList =
//                lessonInfoDao.selectList(queryWrapper);
//        System.out.println(userList);
//    }
//    public List addQiniuUrl(List<LessonInfo> listInput){
//        listInput.forEach(item ->{
//            item.getImgUrl(). = item.getImgUrl();
//        });
//    };
}
