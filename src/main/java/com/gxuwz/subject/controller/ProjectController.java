package com.gxuwz.subject.controller;


import com.gxuwz.subject.model.ProjectModel;
import com.gxuwz.subject.service.IProjectService;
import com.gxuwz.subject.common.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tale
 * @since 2020-03-25
 */
@RestController
@RequestMapping("/project")
public class ProjectController {


    @Autowired
    private IProjectService service;

    @GetMapping("/list")
    public R list(@RequestParam("status")String status, @RequestParam("name")String name,
                  @RequestParam("limit")Integer limit, @RequestParam("page")Integer page,
                  @RequestParam("teacherId")String teacherId){
        List<ProjectModel> list = service.findByName(name, status, teacherId);

        int total = list.size();

        int offset = (page -1) * limit;
        if (page * limit >= total){
            list = list.subList(offset, total);
        }else {
            list = list.subList(offset, page * limit);
        }


        return R.ok().data("list", list).data("total", total);
    }
}
