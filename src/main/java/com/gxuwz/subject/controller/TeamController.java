package com.gxuwz.subject.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.subject.model.TeamModel;
import com.gxuwz.subject.service.ITeamService;
import com.gxuwz.subject.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tale
 * @since 2020-03-25
 */
@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private ITeamService service;

    @RequestMapping("/list")
    public R list(@RequestParam(value = "title", required = false) String title, @RequestParam(value = "teacherId", required = false)String teacherId,
            @RequestParam("limit")String limit, @RequestParam("page")String page){

        IPage<Map<String, Object>> list = new Page<>();

        QueryWrapper<TeamModel> queryWrapper = new QueryWrapper<>();

        queryWrapper.like("title", title);
        queryWrapper.like("teacher_id", teacherId);
        int current = 1;  // 默认第一页
        int size = 10;    // 默认每页10


        if (title != null && "".equals(title)){
            System.out.println("title--->" + title);
            current = Integer.parseInt(title);
        }
        if (limit != null && "".equals(limit)){
            size = Integer.parseInt(limit);
        }


        list = service.pageMaps(new Page<TeamModel>(current, size),queryWrapper);

//        IPage<TeamModel> teamPage = new Page<>();
//
//        List<TeamModel> list = service.findAll(title, teacherId);

        return R.ok().data("list", list);

    }
}
