package com.gxuwz.subject.mapper;

import com.gxuwz.subject.model.ProjectModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tale
 * @since 2020-03-25
 */
public interface ProjectMapper extends BaseMapper<ProjectModel> {

    List<ProjectModel> findByName(@Param("name")String name, @Param("teacherId")String teacherId);
}
