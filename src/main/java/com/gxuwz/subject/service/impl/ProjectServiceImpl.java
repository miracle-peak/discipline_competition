package com.gxuwz.subject.service.impl;

import com.gxuwz.subject.model.ProjectApplyModel;
import com.gxuwz.subject.model.ProjectModel;
import com.gxuwz.subject.mapper.ProjectMapper;
import com.gxuwz.subject.service.IProjectService;
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
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, ProjectModel> implements IProjectService {

    @Autowired
    private ProjectMapper mapper;

    @Override
    public List<ProjectModel> findByName(String name, String teacherId) {
        return mapper.findByName(name, teacherId);
    }
}
