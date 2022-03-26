package com.itheima.controller;

import com.itheima.controller.utils.ApiResult;
import com.itheima.dao.AllTagsDao;
import com.itheima.domain.AllTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {

    @Autowired
    AllTagsDao allTagsDao;

    @GetMapping("/getAllTags")
    public ApiResult getAllTags(){
        return ApiResult.T(allTagsDao.getAllParentTags());
    }

    @GetMapping("/getByTagName")
    public ApiResult getByTagName(AllTags allTags){
        return ApiResult.T(allTagsDao.getByParentTag(allTags.getTagName()));
    }

}
