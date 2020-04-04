package com.gxuwz.subject.service;

import com.gxuwz.subject.mapper.ConclusionMapper;
import com.gxuwz.subject.model.ConclusionModel;
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
public interface IConclusionService extends IService<ConclusionModel> {

    List<ConclusionMapper> findByName(String name, Integer current, Integer limit);

    Integer getTotal(String name);
}
