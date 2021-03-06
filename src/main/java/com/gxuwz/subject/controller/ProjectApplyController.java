package com.gxuwz.subject.controller;

import com.gxuwz.subject.model.ProjectApplyModel;
import com.gxuwz.subject.service.IBudgetService;
import com.gxuwz.subject.service.IProjectApplyService;
import com.gxuwz.subject.service.IProjectService;
import com.gxuwz.subject.common.util.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 * 项目立项
 * @author tale
 * @since 2020-03-25
 */
@RestController
@RequestMapping("/projectApply")
public class ProjectApplyController {
    Logger logger = LoggerFactory.getLogger(ProjectApplyController.class);

    @Autowired
    private IProjectApplyService service;

    @Autowired
    private IBudgetService budgetService;
    @Autowired
    private IProjectService projectService;

    /**
     * 项目立项分页查询
     *
     * @param status
     * @param name
     * @param limit
     * @param page
     * @param teacherId
     * @return
     */
    @GetMapping("/list")
    public R list(@RequestParam("status")String status, @RequestParam("name")String name,
                  @RequestParam("limit")Integer limit, @RequestParam("page")Integer page,
                  @RequestParam("teacherId")String teacherId){
        logger.info("limit---->" + limit);
        int offset = (page - 1) * limit;

        List<ProjectApplyModel> list = service.findByName(name, teacherId, status, offset, limit);

        Integer total = service.getTotal(name, teacherId, status);

        return R.ok().data("list", list).data("total", total);
    }

    /**
     * 项目立项申请
     *
     * @param projectApplyModel
     * @return
     */
    @PostMapping("/save")
    public R add(@RequestBody @Valid ProjectApplyModel projectApplyModel){
        boolean flag;

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

    /**
     * 项目立项修改
     * @param projectApplyModel
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody ProjectApplyModel projectApplyModel){
        System.out.println("params-->" + projectApplyModel.toString());
        boolean flag = service.updateById(projectApplyModel);
        if (! flag){
            return R.error();
        }
        logger.info("teacherId-->" + projectApplyModel.getProject().getTeacherId());

        flag = projectService.updateById(projectApplyModel.getProject());
        if (! flag){
            return R.error();
        }

        flag = budgetService.updateById(projectApplyModel.getBudget());
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }

    }

    /**
     * 项目立项审核
     * @param projectApplyModel
     * @return
     */
    @PostMapping("/examine")
    public R examine(@RequestBody ProjectApplyModel projectApplyModel){
        boolean flag = service.updateById(projectApplyModel);

        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 项目立项申请提交
     * @param projectApplyModel
     * @return
     */
    @PostMapping("/commit")
    public R commit(@RequestBody ProjectApplyModel projectApplyModel){
        logger.info("-->" + projectApplyModel);

        boolean flag = service.updateById(projectApplyModel);

        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 删除单个项目立项
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public R delete(@PathVariable("id") Integer id){

        boolean flag = service.removeById(id);

        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 批量删除项目立项
     * @param ids
     * @return
     */
    @PostMapping("/batchRemove")
    public R batchRemove(@RequestBody Integer[] ids){

        // 不能删除已经通过的项目立项
//        for (Integer id:ids
//             ) {
//
//        }

        boolean flag = service.removeByIds(Arrays.asList(ids));

        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

}
