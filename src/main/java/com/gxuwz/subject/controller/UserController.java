package com.gxuwz.subject.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gxuwz.subject.model.TeacherModel;
import com.gxuwz.subject.model.UserModel;
import com.gxuwz.subject.service.ITeacherService;
import com.gxuwz.subject.service.IUserService;
import com.gxuwz.subject.common.util.*;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ITeacherService teacherService;

    @Autowired
    private JedisUtil jedistUtil;

//    @ApiOperation("登录")
    @RequestMapping("/login")
    @VisitLimit(limit = 3, rangeTime = 5, expire = 60)
    public R login(@RequestBody UserModel userModel){
        if (userModel == null){
            return R.error().message("啥也没有");
        }
        String userName = userModel.getUserName();
        String password = userModel.getPassword();

        if(userName.equals("") || userName == null){
            return R.error().message("请输入用户名");
        }else if (password.equals("") || password == null){
            return R.error().message("请输入密码");
        }

        password = MD5Util.saltEncryption(password);

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_name", userName);
        wrapper.eq("password", password);

        UserModel one = userService.getOne(wrapper);
        if (one != null){
            String token = "";
            boolean flag = true;
            token = jedistUtil.getStr(one.getId() + "");
            System.out.println("getStr---->" + token);
            if (token == null || "".equals(token)) {
                // 保证redis和jwt设置过期时间相同
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.HOUR, 24 * 6); // 设置过期时间
                Date expireTime = calendar.getTime(); // 过期时间
                long time = expireTime.getTime(); // 获取过期时间的时间戳
                System.out.println("过期时间---->" + time);

                token = JWTUtil.createToken(one.getId() + "", one.getUserName(), one.getUtype(), expireTime);

                flag = jedistUtil.setToken(one.getId() + "", token, time); // 存jwt到redis过期时间7天
            }

            if (flag){
                // 如果是教师则返回教师工号
                if (one.getUtype().equals("3")){
                    QueryWrapper queryWrapper = new QueryWrapper();
                    queryWrapper.eq("account_num", one.getUserName());
                    TeacherModel teacherModel = teacherService.getOne(queryWrapper);

                    return R.ok().data("token", token).data("uType", one.getUtype()).data("teacherId", teacherModel.getTeacherId());
                }

                return R.ok().message("登录成功").data("token", token).data("uType", one.getUtype());
            }else {
                R.error().message("服务连接失败！").setCode(ResultCode.CONNECTION_ERROR);
            }

        }

        return R.error().message("密码错误");
    }

    @RequestMapping("/add")
    @ResponseBody
//    @ApiOperation("添加用户")
    public R addUser(@RequestBody UserModel user){

        if (user.getPassword().equals("") || user.getPassword() == null) {

            user.setPassword(MD5Util.saltEncryption(user.getPassword()));

            System.out.println(user.getPassword() + "==" + user.getUserName());

            boolean flag = userService.saveOrUpdate(user);

            if (flag) {
                return R.ok().message("添加用户成功");
            }
        }

        return R.error().message("添加用户失败");
    }


}
