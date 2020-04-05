package com.gxuwz.subject.controller;

import com.gxuwz.subject.common.util.R;
import com.gxuwz.subject.mapper.ConclusionMapper;
import com.gxuwz.subject.model.ConclusionModel;
import com.gxuwz.subject.model.TeamMemberModel;
import com.gxuwz.subject.model.TeamModel;
import com.gxuwz.subject.service.IConclusionService;
import com.gxuwz.subject.service.ITeamMemberService;
import com.gxuwz.subject.service.ITeamService;
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
@RequestMapping("/conclusion")
public class ConclusionController {

    @Autowired
    private IConclusionService service;
    @Autowired
    private ITeamService teamService;
    @Autowired
    private ITeamMemberService memberService;

    @GetMapping("/list")
    public R list(@RequestParam("name")String name,  @RequestParam("limit")Integer limit,
                  @RequestParam("page")Integer page, @RequestParam("teacherId")String teacherId){
        int current = (page - 1) * limit;

        List<ConclusionModel> list = service.findByName(name, current, limit);

        List<TeamMemberModel> memberList = memberService.list();
//        List<TeamModel> teamList = teamService.list();

        Integer total = service.getTotal(name);

//        return R.ok().data("list", list).data("team", teamList);
        return R.ok().data("list", list).data("total", total).data("member", memberList);
    }

}
