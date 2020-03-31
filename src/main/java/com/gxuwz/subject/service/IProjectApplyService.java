package com.gxuwz.subject.service;

import com.gxuwz.subject.model.ProjectApplyModel;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tale
 * @since 2020-03-25
 */
public interface IProjectApplyService extends IService<ProjectApplyModel> {

    List<ProjectApplyModel> findByName(@Param("name")String name, @Param("teacherId")String teacherId);
}
