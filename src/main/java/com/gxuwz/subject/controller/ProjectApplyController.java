package com.gxuwz.subject.controller;

import com.gxuwz.subject.model.ProjectApplyModel;
import com.gxuwz.subject.service.IBudgetService;
import com.gxuwz.subject.service.IProjectApplyService;
import com.gxuwz.subject.service.IProjectService;
import com.gxuwz.subject.common.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping("/projectApply")
public class ProjectApplyController {

    @Autowired
    private IProjectApplyService service;

    @Autowired
    private IBudgetService budgetService;
    @Autowired
    private IProjectService projectService;

    @GetMapping("/list")
    public R list(@RequestParam("status")String status, @RequestParam("name")String name,
                  @RequestParam("limit")Integer limit, @RequestParam("page")Integer page,
                  @RequestParam("teacherId")String teacherId){
        System.out.println("status---->" + status + "teacherId--->" + teacherId);

        List<ProjectApplyModel> list = service.findByName(name, teacherId);

        int total = list.size();

        int offset = (page - 1) * limit;

        if (page * limit >= total){
            list = list.subList(offset, total);
        }else {
            list = list.subList(offset, page * limit);
        }

        return R.ok().data("list", list).data("total", total);
    }

    @PostMapping("/save")
    public R add(@RequestBody ProjectApplyModel projectApplyModel){
        boolean flag = true;

        flag = budgetService.save(projectApplyModel.getBudget());
        if (! flag){
            return R.error();
        }
        Integer budgetId = projectApplyModel.getBudget().getBudgetId();

        flag = projectService.save(projectApplyModel.getProject());
        if (! flag){
            return R.error();
        }
        Integer projectId = projectApplyModel.getProject().getProjectId();

        projectApplyModel.setBudgetId(budgetId);
        projectApplyModel.setProjectId(projectId);

        flag = service.save(projectApplyModel);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }

    }


    @PostMapping("/update")
    public R update(@RequestBody ProjectApplyModel projectApplyModel){
        boolean flag = true;

        flag = service.updateById(projectApplyModel);

        flag = projectService.updateById(projectApplyModel.getProject());
        if (! flag){
            return R.error();
        }

        flag = budgetService.updateById(projectApplyModel.getBudget());
        if (! flag){
            return R.error();
        }


        System.out.println("sum-->" + projectApplyModel.getBudget().getSum());
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }


    }

}
