package com.gxuwz.subject.service;

import com.gxuwz.subject.model.ConclusionModel;
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
public interface IConclusionService extends IService<ConclusionModel> {

    List<ConclusionModel> findByName(String name, Integer current, Integer limit,
                                     String teacherId, String status);

    Integer getTotal(String name, String teacherId, String status);
}
