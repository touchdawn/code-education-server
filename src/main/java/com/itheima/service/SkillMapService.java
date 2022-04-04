package com.itheima.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.SkillMap;

public interface SkillMapService extends IService<SkillMap> {
    ApiResult getSkillMapById(Integer id);
}
