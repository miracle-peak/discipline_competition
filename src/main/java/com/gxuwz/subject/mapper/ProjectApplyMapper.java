package com.gxuwz.subject.mapper;

import com.gxuwz.subject.model.ProjectApplyModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
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
@Mapper
public interface ProjectApplyMapper extends BaseMapper<ProjectApplyModel> {

    List<ProjectApplyModel> findByName(@Param("name")String name,
          @Param("teacherId")String teacherId, @Param("status")String status,
          @Param("current")Integer current, @Param("limit")Integer limit);

    Integer getTotal(@Param("name")String name, @Param("teacherId")String teacherId,
                     @Param("status")String status);
}
