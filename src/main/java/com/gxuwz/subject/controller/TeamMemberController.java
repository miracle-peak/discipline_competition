package com.gxuwz.subject.controller;


import com.gxuwz.subject.model.TeamMemberModel;
import com.gxuwz.subject.service.ITeamMemberService;
import com.gxuwz.subject.common.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tale
 * @since 2020-03-25
 */
@RestController
@RequestMapping("/teamMember")
public class TeamMemberController {

    @Autowired
    private ITeamMemberService service;

    @PostMapping("/delete")
    public R delete(@RequestBody TeamMemberModel teamMemberModel){
        System.out.println("delete--->" + teamMemberModel);

        boolean flag = service.removeById(teamMemberModel);
        if (flag){
            return R.ok();
        }

        return R.error();
    }


}
