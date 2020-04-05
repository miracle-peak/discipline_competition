package com.gxuwz.subject.service.impl;

import com.gxuwz.subject.model.TeamModel;
import com.gxuwz.subject.mapper.TeamMapper;
import com.gxuwz.subject.service.ITeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tale
 * @since 2020-03-25
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, TeamModel> implements ITeamService {

    @Autowired
    private TeamMapper teamMapper;

    @Override
    public List<TeamModel> findAll(String title, String teacherId, Integer current,
                                   Integer limit) {
        return teamMapper.findAll(title, teacherId, current, limit);
    }

    @Override
    public Integer getTotal(String title, String teacherId) {
        return teamMapper.getTotal(title, teacherId);
    }

    @Override
    public List<TeamModel> findByTeacher(String title, String teacherId) {
        return teamMapper.findByTeacher(title, teacherId);
    }
}
