package com.gxuwz.subject.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gxuwz.subject.common.annotation.Log;
import com.gxuwz.subject.common.annotation.VisitLimit;
import com.gxuwz.subject.common.constant.StatusCode;
import com.gxuwz.subject.common.rabbit.RabbitProducer;
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

import java.time.LocalDateTime;
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
    private RedisUtil redisUtil;

    @Autowired
    private RabbitProducer rabbitProducer;

//    @ApiOperation("登录")
    @PostMapping("/login")
    @Log
    @VisitLimit(limit = 3, rangeTime = 5, expire = 60)
    public R login(@RequestBody UserModel userModel){
//    public R login(@RequestBody @Validated UserModel userModel){
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
            String token  = redisUtil.getStr(one.getId() + "");

            // 不存在这个token 即第一次登录或者过期删除
            if (StringUtils.isEmpty(token)) {
                // 保证redis和jwt设置过期时间相同
                Calendar calendar = Calendar.getInstance();
                // 设置过期时间
                calendar.add(Calendar.HOUR, 24 * 6);
                // 过期时间
                Date expireTime = calendar.getTime();
                // 获取过期时间的时间戳, 使用同一个 expireTime 保证 jwt 和 redis key过期时间一致
                // TODO 这个是错的 long time = expireTime.getTime();
                long time = expireTime.getTime() - System.currentTimeMillis();
                // 创建jwt
                token = JwtUtil.createToken(one.getId() + "", one.getUserName(), one.getUtype(), expireTime);
                // 存jwt到redis过期时间6天
                redisUtil.setToken(one.getId() + "", token, time);
            }
            // 如果是教师则返回教师工号
            if ("3".equals(one.getUtype())){
                QueryWrapper queryWrapper = new QueryWrapper();
                queryWrapper.eq("account_num", one.getUserName());
                TeacherModel teacherModel = teacherService.getOne(queryWrapper);

                return R.ok().data("token", token).data("uType", one.getUtype()).data("teacherId", teacherModel.getTeacherId());
            }

            return R.ok().message("登录成功").data("token", token).data("uType", one.getUtype());


        }
        return R.error().message("用户名或密码错误");

    }

//    @ApiOperation("添加用户")
    @PostMapping("/add")
    @ResponseBody
    @VisitLimit(limit = 3, rangeTime = 8, expire = 60)
    public R addUser(@RequestBody UserModel user){
        // TODO 需要验证邮箱 用户名 但使用@Validated返回响应不友好
        if (StringUtils.isEmpty(user.getMail())){
            return R.error().message("请输入邮箱");
        }
        if (! StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(MD5Util.saltEncryption(user.getPassword()));

            boolean flag = userService.saveOrUpdate(user);
            if (flag) {
                // 将用户存到rabbitmq,用于发送qq邮箱通知
                rabbitProducer.send(user);

                return R.ok().message("注册成功" + LocalDateTime.now());
            }
        }

        return R.error().message("注册失败");
    }


}
