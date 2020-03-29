package com.gxuwz.subject.mapper;

import com.gxuwz.subject.model.TeamModel;
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
public interface TeamMapper extends BaseMapper<TeamModel> {

    List<TeamModel> findAll(@Param("title")String title, @Param("teacherId")String teacherId);
}
