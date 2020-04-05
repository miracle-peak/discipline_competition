package com.gxuwz.subject.service;

import com.gxuwz.subject.model.ProjectModel;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tale
 * @since 2020-03-25
 */
public interface IProjectService extends IService<ProjectModel> {

    List<ProjectModel> findByName(String name,String status, String teacherId);
}
