package com.gxuwz.subject.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.subject.model.TeamModel;
import com.gxuwz.subject.service.ITeamService;
import com.gxuwz.subject.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
    public R list(@RequestBody Map<String, Object> params){
//        public R list(@RequestParam(value = "title", required = false) String title, @RequestParam(value = "teacherId", required = false)String teacherId,
//            @RequestParam("limit")String limit, @RequestParam("page")String page){
        String title = "";
        String teacherId = "";
        Integer limit = null;
        Integer page = null;


        if (params.containsKey("title")){
            title = (String)params.get("title");
        }
        if (params.containsKey("teacherId")){
            teacherId = (String)params.get("teacherId");
        }
        if (params.containsKey("limit")){
            limit = (Integer)params.get("limit");
        }
        if (params.containsKey("page")){
            page = (Integer)params.get("page");
        }

        System.out.println("team---teacherId--->" + teacherId);

        List<TeamModel> list = service.findAll(title, teacherId);
        int total = list.size();

        int offset = (page - 1) * limit;

        if (page * limit >= total){
            list = list.subList(offset, total);
        }else {
            list = list.subList(offset, page * limit);
        }


        return R.ok().data("list", list).data("total", total);

    }
}
