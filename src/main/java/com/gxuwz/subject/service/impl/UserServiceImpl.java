package com.gxuwz.subject.service.impl;

import com.gxuwz.subject.model.UserModel;
import com.gxuwz.subject.mapper.UserMapper;
import com.gxuwz.subject.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tale
 * @since 2020-03-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserModel> implements IUserService {

}
