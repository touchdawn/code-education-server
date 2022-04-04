package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.controller.utils.ApiResult;
import com.itheima.dao.SkillMapDao;
import com.itheima.domain.SkillMap;
import com.itheima.service.SkillMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SkillMapServiceImpl extends ServiceImpl<SkillMapDao, SkillMap> implements SkillMapService {

    @Autowired
    SkillMapDao skillMapDao;

    @Override
    public ApiResult getSkillMapById(Integer id) {
        List<Map> skillMapByParentId = skillMapDao.getSkillMapByParentId(id);
        return ApiResult.T(skillMapByParentId);
    }
}
