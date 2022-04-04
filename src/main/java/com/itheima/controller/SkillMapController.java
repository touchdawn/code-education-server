package com.itheima.controller;


import com.itheima.controller.utils.ApiResult;
import com.itheima.service.SkillMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/skillMap")
public class SkillMapController {
    @Autowired
    SkillMapService skillMapService;

    @GetMapping("/getSkillMapById/{id}")
    public ApiResult getSkillMapById(@PathVariable Integer id){
        return skillMapService.getSkillMapById(id);
    }
}
