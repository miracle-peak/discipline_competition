package com.gxuwz.subject.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gxuwz.subject.common.util.R;
import com.gxuwz.subject.model.TeamMemberModel;
import com.gxuwz.subject.model.TeamModel;
import com.gxuwz.subject.service.ITeamMemberService;
import com.gxuwz.subject.service.ITeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
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
    Logger logger = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    private ITeamService service;
    @Autowired
    private ITeamMemberService memberService;

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

        logger.info("team---teacherId--->" + teacherId);

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

    @PostMapping("/add")
    public R add(@RequestBody TeamModel teamModel){
        System.out.println("saveOrUpdate--->" + teamModel);

        // 生成团队编号
        long currentTimeMillis = System.currentTimeMillis();
        teamModel.setTeamNo(currentTimeMillis + "");
        List<TeamMemberModel> memberList = teamModel.getMemberList();
        for (TeamMemberModel member:memberList
             ) {
            member.setTeamNo(currentTimeMillis + "");
        }
        teamModel.setMemberList(memberList);

        boolean flag = memberService.saveBatch(memberList);

        if (! flag){
            return R.error();
        }
        flag = service.save(teamModel);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }

    }

    @PostMapping("/update")
    public R update(@RequestBody TeamModel teamModel){
        boolean flag = memberService.updateBatchById(teamModel.getMemberList());

        if (! flag){
            return R.error();
        }

        flag = service.updateById(teamModel);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }

    }

    /**
     * 删删除团队信息
     * @param teamNos
     * @return
     */
    @PostMapping("/batchRemove")
    public R batchRemove(@RequestBody String[] teamNos){
        logger.info("batchRemove--->" + teamNos);

        boolean flag = service.removeByIds(Arrays.asList(teamNos));

        if (! flag){
            return R.error();
        }

        // 也可以不删除成员信息
        // 一下是删除批量成员
        QueryWrapper<TeamMemberModel> queryWrapper = new QueryWrapper();

        List<Integer> memberNos = new ArrayList<>();
        for (String id: teamNos
             ) {
            queryWrapper.eq("team_no", id);

            List<TeamMemberModel> memberList = memberService.list(queryWrapper);
            for (TeamMemberModel member: memberList
                 ) {
                memberNos.add(member.getMemberNo());
            }
        }
        logger.info("memberNos---->" + memberNos);

        flag = memberService.removeByIds(memberNos);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }

    }

}
