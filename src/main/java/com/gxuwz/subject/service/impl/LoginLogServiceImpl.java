package com.gxuwz.subject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxuwz.subject.mapper.LoginLogMapper;
import com.gxuwz.subject.model.LoginLogModel;
import com.gxuwz.subject.service.ILoginLogService;
import org.springframework.stereotype.Service;

/**
 * author: 蔡奇峰
 * date: 2020/5/1 15:00
 **/
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLogModel> implements ILoginLogService {
}
