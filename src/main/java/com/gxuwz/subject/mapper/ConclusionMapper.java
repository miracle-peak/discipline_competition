package com.gxuwz.subject.mapper;

import com.gxuwz.subject.model.ConclusionModel;
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
public interface ConclusionMapper extends BaseMapper<ConclusionModel> {

    List<ConclusionModel> findByName(@Param("name")String name, @Param("current")Integer current,
            @Param("limit")Integer limit, @Param("teacherId")String teacherId,
              @Param("status")String status);

    Integer getTotal(@Param("name")String name, @Param("teacherId")String teacherId,
                     @Param("status")String status);
}
