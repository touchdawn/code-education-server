package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.controller.utils.ApiResult;
import com.itheima.dao.FileStorageDao;
import com.itheima.dao.ImgStorageDao;
import com.itheima.dao.LessonChapterSectionDao;
import com.itheima.dao.LessonInfoDao;
import com.itheima.domain.FileStorage;
import com.itheima.domain.LessonChapterSection;
import com.itheima.domain.LessonInfo;
import com.itheima.service.FileStorageService;
import com.itheima.service.LessonChapterSectionService;
import com.itheima.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LessonServiceImpl extends ServiceImpl<LessonInfoDao, LessonInfo> implements LessonService {

    @Autowired
    private LessonInfoDao lessonInfoDao;

    @Autowired
    private FileStorageDao fileStorageDao;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private LessonChapterSectionService lessonChapterSectionService;

    @Autowired
    private LessonChapterSectionDao  lessonChapterSectionDao;

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
    public ApiResult getCourseInfo(Integer courseId){
//        ImgStorage courseImgInfo = imgStorageDao.findByCourseId(courseId);
        Map<String,Object> map = new HashMap<>();
//        map.put("imgUrl",courseImgInfo.getImgUrl());
        LessonInfo courseMainInfo = lessonInfoDao.selectById(courseId);
        map.put("introduction",courseMainInfo.getCourseIntroduction());
        map.put("subscribeNum",courseMainInfo.getSubscribeNum().toString());
        map.put("score",courseMainInfo.getScore().toString());
        map.put("imgUrl",courseMainInfo.getImgUrl());
        map.put("courseType",courseMainInfo.getCourseType());

        List<Object> chapterList = new ArrayList<>();

        List<Map> courseChapterSectionInfo = lessonChapterSectionDao.getCourseChapterInfo(courseId);
        courseChapterSectionInfo.forEach(obj ->{
            Integer parentId = (Integer) obj.get("ID");
            List<Map> chapterChildInfo = lessonChapterSectionDao.getChapterChildInfo(parentId);
            obj.put("child",chapterChildInfo);
            chapterList.add(obj);
        });

        map.put("chapter",chapterList);
        return ApiResult.T(map);
    }

    @Override
    public ApiResult addNewCourse(Map<String, String> map) {
        LessonInfo lessonInfo = new LessonInfo();
        lessonInfo.setLessonName(map.get("courseName"));
        lessonInfo.setCourseIntroduction(map.get("courseIntro"));
        lessonInfo.setStatus(0);
        lessonInfo.setScore(5);
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
            lessonChapterSectionService.save(lessonChapterSection);
//        } catch (Exception e) {
//            return ApiResult.F("", String.valueOf(e));
//        }
        return ApiResult.T();
    }



//    public List addQiniuUrl(List<LessonInfo> listInput){
//        listInput.forEach(item ->{
//            item.getImgUrl(). = item.getImgUrl();
//        });
//    };
}
