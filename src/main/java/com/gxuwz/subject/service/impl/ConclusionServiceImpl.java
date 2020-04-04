package com.gxuwz.subject.service.impl;

import com.gxuwz.subject.model.ConclusionModel;
import com.gxuwz.subject.mapper.ConclusionMapper;
import com.gxuwz.subject.service.IConclusionService;
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
public class ConclusionServiceImpl extends ServiceImpl<ConclusionMapper, ConclusionModel> implements IConclusionService {

    @Autowired
    private ConclusionMapper mapper;

    @Override
    public List<ConclusionMapper> findByName(String name, Integer current, Integer limit) {
        return mapper.findByName(name, current, limit);
    }

    @Override
    public Integer getTotal(String name) {
        return mapper.getTotal(name);
    }
}
