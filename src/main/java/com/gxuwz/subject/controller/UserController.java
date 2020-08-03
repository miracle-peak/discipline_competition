package com.gxuwz.subject.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gxuwz.subject.common.annotation.Log;
import com.gxuwz.subject.common.annotation.VisitLimit;
import com.gxuwz.subject.common.constant.StatusCode;
import com.gxuwz.subject.common.rabbit.RabbitProducer;
import com.gxuwz.subject.model.JwtValidate;
import com.gxuwz.subject.model.TeacherModel;
import com.gxuwz.subject.model.UserModel;
import com.gxuwz.subject.service.ITeacherService;
import com.gxuwz.subject.service.IUserService;
import com.gxuwz.subject.common.util.*;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tale
 * @since 2020-03-25
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ITeacherService teacherService;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private JedisUtil jedistUtil;

    @Autowired
    private RabbitProducer rabbitProducer;

//    @ApiOperation("登录")
    @PostMapping("/login")
    @Log
    @VisitLimit(limit = 3, rangeTime = 5, expire = 60)
    public R login(@RequestBody UserModel userModel){
        // 这个应该是不会被触发
        if (ObjectUtils.isEmpty(userModel)){
            return R.error().message("啥也没有");
        }
        String userName = userModel.getUserName();
        String password = userModel.getPassword();

        if(StringUtils.isEmpty(userName)){
            return R.error().message("请输入用户名");
        }else if (StringUtils.isEmpty(password)){
            return R.error().message("请输入密码");
        }
        // 密码加盐加密
        password = MD5Util.saltEncryption(password);

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_name", userName);
        wrapper.eq("password", password);

        UserModel one = userService.getOne(wrapper);

        if (! ObjectUtils.isEmpty(one)){
            String token = "";
            boolean flag = true;
            token = jedistUtil.getStr(one.getId() + "");

            // 保证redis和jwt设置过期时间相同
            Calendar calendar = Calendar.getInstance();
            // 设置过期时间
            calendar.add(Calendar.HOUR, 24 * 6);
            // 过期时间
            Date expireTime = calendar.getTime();

            // 验证jwt
            flag = tokenUtil.valid(one, token, expireTime);

            if (flag){
                // 如果是教师则返回教师工号
                if ("3".equals(one.getUtype())){
                    QueryWrapper queryWrapper = new QueryWrapper();
                    queryWrapper.eq("account_num", one.getUserName());
                    TeacherModel teacherModel = teacherService.getOne(queryWrapper);

                    return R.ok().data("token", token).data("uType", one.getUtype()).data("teacherId", teacherModel.getTeacherId());
                }

                return R.ok().message("登录成功").data("token", token).data("uType", one.getUtype());
            }else {
                return R.error().message("服务连接失败！").code(StatusCode.CONNECTION_ERROR);
            }

        }
        return R.error().message("用户名或密码错误");

    }

//    @ApiOperation("添加用户")
    @PostMapping("/add")
    @ResponseBody
    @VisitLimit(limit = 3, rangeTime = 8, expire = 60)
    public R addUser(@RequestBody UserModel user){
        if (StringUtils.isEmpty(user)) {
            return R.error().message("请输入注册信息！");
        }
        if (! "".equals(user.getPassword()) && user.getPassword() != null) {
            user.setPassword(MD5Util.saltEncryption(user.getPassword()));

            boolean flag = userService.saveOrUpdate(user);
            if (flag) {
                // 将用户存到rabbitmq,用于发送qq邮箱通知
                rabbitProducer.send(user);

                return R.ok().message("添加用户成功");
            }
        }

        return R.error().message("添加用户失败");
    }


}
