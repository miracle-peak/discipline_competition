package com.gxuwz.subject.controller;

import com.gxuwz.subject.common.util.R;
import com.gxuwz.subject.mapper.ConclusionMapper;
import com.gxuwz.subject.model.ConclusionModel;
import com.gxuwz.subject.model.TeamMemberModel;
import com.gxuwz.subject.model.TeamModel;
import com.gxuwz.subject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 * 项目结题
 * @author tale
 * @since 2020-03-25
 */
@RestController
@RequestMapping("/conclusion")
public class ConclusionController {

    @Autowired
    private IConclusionService service;
    @Autowired
    private ITeamService teamService;
    @Autowired
    private ITeamMemberService memberService;
    @Autowired
    private IPrizeService prizeService;
    @Autowired
    private ICapitalService capitalService;


    @GetMapping("/list")
    public R list(@RequestParam("name")String name,  @RequestParam("limit")Integer limit,
                  @RequestParam("page")Integer page, @RequestParam("teacherId")String teacherId,
                  @RequestParam("status")String status){
        int current = (page - 1) * limit;

        List<ConclusionModel> list = service.findByName(name, current, limit, teacherId, status);

        List<TeamMemberModel> memberList = memberService.list();
//        List<TeamModel> teamList = teamService.list();

        Integer total = service.getTotal(name, teacherId, status);

//        return R.ok().data("list", list).data("team", teamList);
        return R.ok().data("list", list).data("total", total).data("member", memberList);
    }


    /**
     * 项目结题申请
     * @param conclusionModel
     * @return
     */
    @PostMapping("/add")
    public R add(@RequestBody ConclusionModel conclusionModel){

        boolean flag = capitalService.save(conclusionModel.getCapital());
        if (! flag){
            return R.error();
        }
        flag = prizeService.save(conclusionModel.getPrize());
        if (! flag){
            return R.error();
        }

        Integer capitalId = conclusionModel.getCapital().getCapitalId();
        Integer prizeId = conclusionModel.getPrize().getPrizeId();

        conclusionModel.setCapitalId(capitalId);
        conclusionModel.setPrizeId(prizeId);

        flag = service.save(conclusionModel);
        if (! flag){
            return R.error();
        }

        return R.ok();
    }

    /**
     * 项目结题修改
     * @param conclusionModel
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody ConclusionModel conclusionModel){

        boolean flag = capitalService.updateById(conclusionModel.getCapital());
        if (! flag){
            return R.error();
        }
        flag = prizeService.updateById((conclusionModel.getPrize()));

        if (! flag){
            return R.error();
        }

        flag = service.updateById(conclusionModel);

        if (! flag){
            return R.error();
        }

        return R.ok();
    }

    /**
     * 项目结题提交
     * @return
     */
    @PostMapping("/commit")
    public R commit(@RequestBody ConclusionModel conclusionModel){

        boolean flag = service.updateById(conclusionModel);
        if (! flag){
            return R.error();
        }

        return R.ok();
    }

    @PostMapping("delete")
    public R delete(@RequestBody Integer[] ids){

        boolean flag = service.removeByIds(Arrays.asList(ids));
        if (! flag){
            return R.error();
        }

        return R.ok();
    }

}
