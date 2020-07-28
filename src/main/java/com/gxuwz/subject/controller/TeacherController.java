package com.gxuwz.subject.controller;


import com.alibaba.fastjson.JSON;
import com.gxuwz.subject.common.constant.StatusCode;
import com.gxuwz.subject.common.util.JedisUtil;
import com.gxuwz.subject.model.TeacherModel;
import com.gxuwz.subject.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tale
 * @since 2020-03-25
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private ITeacherService service;

    @Autowired
    private JedisUtil jedisUtil;


    /**
     * 查询全部教师
     * 这里的方法类型应为 com.gxuwz.subject.common.util.R
     *
     * @return
     */
    @Cacheable(cacheNames = "list")
    @GetMapping("/list")
    public List<TeacherModel> list() {
        List<TeacherModel> list = service.list();

        /*String str = jedisUtil.getStr(StatusCode.TEACHER_KEY);
        if (StringUtils.isEmpty(str)) {

            list = service.list();
        } else {
            list = JSON.parse(str);
        }*/


        return list;
    }

}
