package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.controller.utils.ApiResult;
import com.itheima.dao.*;
import com.itheima.domain.*;
import com.itheima.service.FileStorageService;
import com.itheima.service.LessonChapterSectionService;
import com.itheima.service.LessonService;
import com.itheima.service.UserMessageService;
import com.itheima.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Arrays.asList;

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
    private UserMessageService userMessageService;

    @Autowired
    private LessonChapterSectionService lessonChapterSectionService;

    @Autowired
    private LessonChapterSectionDao  lessonChapterSectionDao;

    @Autowired
    private UserFavouriteDao userFavouriteDao;

    @Autowired
    RedisUtil redisUtil;

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

        if(findCourseLike(courseId,userId)) {
            map.put("isLike",true);
            map.put("isDislike",false);
        } else if (findCourseDislike(courseId,userId)) {
            map.put("isDislike",true);
            map.put("isLike",false);
        } else {
            map.put("isDislike",false);
            map.put("isLike",false);
        }

        map.put("likeNum",getCourseLikeNum(courseId));
        if (findCourseSubscribe(courseId,userId)) {
            map.put("isSubscribe",true);
        } else {
            map.put("isSubscribe",false);
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

    @Override
    public ApiResult addCourseLike(Integer courseId, Integer userId) {
        redisUtil.sAdd(courseId + "Like" , userId.toString());
        redisUtil.sRemove(courseId+ "Dislike",userId.toString());
//        return ApiResult.T(redisUtil.sIsMember(courseId + "Like",String.valueOf(userId)));
        return ApiResult.T(redisUtil.sHasKey(courseId + "Like",userId));
    }

    @Override
    public ApiResult cancelCourseLike(Integer courseId, Integer userId) {
        return ApiResult.T(redisUtil.sRemove(courseId+ "Like",userId.toString()));
    }

    @Override
    public Boolean findCourseLike(Integer courseId, Integer userId){
        return redisUtil.sHasKey(courseId + "Like",userId);
    }

    @Override
    public ApiResult addCourseDislike(Integer courseId, Integer userId) {
        redisUtil.sAdd(courseId + "Dislike" , userId.toString());
        redisUtil.sRemove(courseId+ "Like",userId.toString());
        return ApiResult.T(redisUtil.sHasKey(courseId + "Dislike",userId));
    }

    @Override
    public ApiResult cancelCourseDislike(Integer courseId, Integer userId) {
        return ApiResult.T(redisUtil.sRemove(courseId+ "Dislike",userId.toString()));
    }

    @Override
    public Boolean findCourseDislike(Integer courseId, Integer userId){
        return redisUtil.sHasKey(courseId + "Dislike",userId);
    }

    @Override
    public Long getCourseLikeNum(Integer courseId) {
        return redisUtil.sSize(courseId + "Like");
    }

    @Override
    public ApiResult addCourseSubscribe(Integer courseId, Integer userId) {
//        redisUtil.zAdd(courseId + "Subscribe" , userId.toString(),new Date().getTime());
        redisUtil.sAdd(courseId + "Subscribe" , userId.toString());
        redisUtil.lLeftPush("User" + userId, courseId.toString());
        return ApiResult.T(redisUtil.sHasKey(courseId + "Subscribe",userId));
    }

    @Override
    public ApiResult cancelCourseSubscribe(Integer courseId, Integer userId) {
//        redisUtil.sRemove("User" + userId, courseId.toString());
        redisUtil.lRemove("User" + userId,0,courseId.toString());
        return ApiResult.T(redisUtil.sRemove(courseId+ "Subscribe",userId.toString()));
    }

    @Override
    public ApiResult getCourseSubscribeNum(Integer courseId) {
        return ApiResult.T(redisUtil.sSize(courseId + "Subscribe"));
    }

    @Override
    public Boolean findCourseSubscribe(Integer courseId, Integer userId) {
        return redisUtil.sHasKey(courseId + "Subscribe",userId);
    }

    @Override
    public ApiResult getSubCourseByUserId(Integer userId) {
        List<String> strings = redisUtil.lRange("User" + userId,0,-1);
        List list=new ArrayList();

        strings.forEach(str->{
            LessonInfo lessonInfo = lessonInfoDao.selectById(Integer.parseInt(str));
            list.add(lessonInfo);
        });
        return ApiResult.T(list);

//        Iterator i = strings.iterator();
//        List list=new ArrayList();
//        while (i.hasNext()){
//        LessonInfo lessonInfo = lessonInfoDao.selectById(Integer.parseInt(i.next().toString()));
//            list.add(lessonInfo);
//        }
//        return ApiResult.T(list);
    }






//
//    @Override
//    public ApiResult searchCourse(Map searchForm) {
//        QueryWrapper<LessonInfo> queryWrapper = new QueryWrapper();
//        Map queryMap = new HashMap<>();
////        Object searchData = searchForm.get("searchData");
////        Map stringObjectMap =  new HashMap<>();
//
////        if (searchForm.get("status") != null){
////            queryMap.put("status",searchForm.get("status"));
////        }
////        if (searchForm.get("teacherName") != null){
////            queryMap.put("teacherName",searchForm.get("teacherName"));
////        }
//
//
////        if (searchForm.get("id") != null){
////            int id = Integer.parseInt(searchForm.get("id").toString());
////            stringObjectMap.put("id",searchForm.get("id").toString());
////        }
//
////        Object searchData = searchForm.get("searchData");
////        try {
////            stringObjectMap = objectToMap(searchData);
////        } catch (Exception e) {
////        }
//        queryWrapper.allEq(searchForm);
//        System.out.println(queryWrapper.lambda());
//        System.out.println(queryWrapper.getSqlSelect());
//        System.out.println(1);
//        List<LessonInfo> lessonInfos = lessonInfoDao.selectList(queryWrapper);
//        return ApiResult.T(queryWrapper);
//    }
//
////        Iterator i = strings.iterator();
////        List list=new ArrayList();
////        while (i.hasNext()){
////        LessonInfo lessonInfo = lessonInfoDao.selectById(Integer.parseInt(i.next().toString()));
////            list.add(lessonInfo);
////        }
//
//
//
////    public void findByLike() {
////        QueryWrapper<LessonInfo> queryWrapper = new QueryWrapper<LessonInfo>();
////        queryWrapper.like("name", "%乔%");
////        List<LessonInfo> userList =
////                lessonInfoDao.selectList(queryWrapper);
////        System.out.println(userList);
////    }
////    public List addQiniuUrl(List<LessonInfo> listInput){
////        listInput.forEach(item ->{
////            item.getImgUrl(). = item.getImgUrl();
////        });
////    };


    @Override
    public ApiResult getCourseBySearch(Map searchForm) {
        LambdaQueryWrapper<LessonInfo> queryWrapper = new LambdaQueryWrapper<>();
        Map searchData = (Map) searchForm.get("searchData");
        if (searchData.get("lessonName") != null){
            queryWrapper.like(true,LessonInfo::getLessonName,searchData.get("lessonName").toString());
        }
        if (searchData.get("teacherId") != null){
            queryWrapper.eq(true,LessonInfo::getCreatorId,searchData.get("teacherId").toString());
        }
        if (searchData.get("status") != null){
            queryWrapper.eq(true,LessonInfo::getStatus,searchData.get("status").toString());
        }
        IPage page = lessonInfoDao.selectPage(new Page((Integer)searchForm.get("current"),(Integer)searchForm.get("size")),queryWrapper);
        return ApiResult.T(page);

    }

    @Override
    public ApiResult getCourseByQuery() {

        List<Map> status = lessonChapterSectionDao.getAuditingSection();

        return ApiResult.T(status);
    }



    @Override
    public ApiResult updateSectionStatus(LessonChapterSection lessonChapterSection,String auditOpinion) {

        Integer status = lessonChapterSectionDao.selectById(lessonChapterSection.getId()).getStatus();
        if (status == 0 && lessonChapterSection.getStatus() == 1){
            String messageContent = "尊敬的用户：\n"+
                    "      您上传的《" + lessonChapterSection.getTitle() + "》章节通过审核啦，快去看看吧~";
            LessonInfo lessonInfo = lessonInfoDao.selectById(lessonChapterSection.getLessonRel());
            Map<String,String> map = new HashMap<>();
            map.put("senderId","1");
            map.put("receiverId",lessonInfo.getCreatorId().toString());
            map.put("title","课程章节审核通过通知");
            map.put("content",messageContent);
            map.put("parentMessageId","-1");
            Integer callbackId = userMessageService.addMessage(map);
        } else if (status == 0 && lessonChapterSection.getStatus() == 0 && !Objects.equals(auditOpinion, "审核通过")){
            String messageContent = "尊敬的用户：\n"+
                    "      您上传的《" + lessonChapterSection.getTitle() + "》章节未通过审核，请检查后重新上传~" +
                    "      审核意见：" + auditOpinion;
            LessonInfo lessonInfo = lessonInfoDao.selectById(lessonChapterSection.getLessonRel());
            Map<String,String> map = new HashMap<>();
            map.put("senderId","1");
            map.put("receiverId",lessonInfo.getCreatorId().toString());
            map.put("title","课程章节审核未通过");
            map.put("content",messageContent);
            map.put("parentMessageId","-1");
            System.out.println("拒绝了一个审核");
            Integer callbackId = userMessageService.addMessage(map);
            System.out.println("===================");
            System.out.println(callbackId);;
        }
        lessonChapterSectionDao.updateById(lessonChapterSection);

        return ApiResult.T();
    }

    @Override
    public ApiResult getCourseByQueryPage(Map page) {
        Integer sectionCount = lessonChapterSectionDao.getSectionCountByStatus(0);
        String current = page.get("current").toString();
        String size = page.get("size").toString();
        Integer currentPage = Integer.parseInt(current) - 1;
        Integer sizePage = Integer.parseInt(size);
        Integer totalPage = sectionCount/sizePage;
        if (sectionCount%sizePage != 0){
            totalPage = totalPage + 1;
        }
        if (currentPage > totalPage){
            return ApiResult.F("","当前页数大于总页数");
        }
        Map map = new HashMap();
        map.put("current",currentPage);
        map.put("size",sizePage);
        map.put("total",sectionCount);
        List<Map> status = lessonChapterSectionDao.getAuditingSectionByPage(currentPage * sizePage,sizePage);
        map.put("data",status);
        return ApiResult.T(map);
    }


//    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
//        Map<String, Object> map = new HashMap<String,Object>();
//        Class<?> clazz = obj.getClass();
//        for (Field field : clazz.getDeclaredFields()) {
//            field.setAccessible(true);
//            String fieldName = field.getName();
//            Object value = field.get(obj);
//            map.put(fieldName, value);
//        }
//        return map;
//    }

}
