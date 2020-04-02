package com.gxuwz.subject.service.impl;

import com.gxuwz.subject.model.ProjectApplyModel;
import com.gxuwz.subject.mapper.ProjectApplyMapper;
import com.gxuwz.subject.service.IProjectApplyService;
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
public class ProjectApplyServiceImpl extends ServiceImpl<ProjectApplyMapper, ProjectApplyModel> implements IProjectApplyService {

    @Autowired
    private ProjectApplyMapper mapper;

    @Override
    public List<ProjectApplyModel> findByName(String name, String teacherId, String status) {
        return mapper.findByName(name, teacherId, status);
    }
}
