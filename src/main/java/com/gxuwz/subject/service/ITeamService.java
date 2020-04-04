package com.gxuwz.subject.service;

import com.gxuwz.subject.model.TeamModel;
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
public interface ITeamService extends IService<TeamModel> {

    List<TeamModel> findAll(String title, String teacherId,
                            Integer current, Integer limit);

    Integer getTotal(String title, String teacherId);
}
