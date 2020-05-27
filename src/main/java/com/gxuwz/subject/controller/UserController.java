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

    @Autowired
    private RabbitProducer rabbitProducer;

//    @ApiOperation("登录")
    @RequestMapping("/login")
    @Log
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

        if (! ObjectUtils.isEmpty(one)){
            String token = "";
            boolean flag = true;
            token = jedistUtil.getStr(one.getId() + "");

            // 保证redis和jwt设置过期时间相同
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR, 24 * 6); // 设置过期时间
            Date expireTime = calendar.getTime(); // 过期时间
            long time = expireTime.getTime(); // 获取过期时间的时间戳

            if (token == null || "".equals(token)) {// 不存在这个token
                // 创建jwt
                token = JWTUtil.createToken(one.getId() + "", one.getUserName(), one.getUtype(), expireTime);

                // 把jwt存到redis
                flag = jedistUtil.setToken(one.getId() + "", token, time); // 存jwt到redis过期时间7天
            }else{// 存在jwt（token）

                // 验证jwt
                JwtValidate validate = JWTUtil.validateJwt(token);

                if (! validate.isSuccess()){// 验证不通过

                    // jwt过期
                    if (validate.getErrCode() == StatusCode.JWT_EXPIRE){
                        System.out.println("登录--expire-->");
                        jedistUtil.deleteStr(one.getId() + "");
                        // 创建jwt
                        token = JWTUtil.createToken(one.getId() + "", one.getUserName(), one.getUtype(), expireTime);

                        // 把jwt存到redis
                        flag = jedistUtil.setToken(one.getId() + "", token, time); // 存jwt到redis过期时间7天
                    }
                    // TODO 其他错误未处理
                }

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
                R.error().message("服务连接失败！").setCode(StatusCode.CONNECTION_ERROR);
            }

        }
        return R.error().message("密码错误");

    }

//    @ApiOperation("添加用户")
    @RequestMapping("/add")
    @ResponseBody
    @VisitLimit(limit = 3, rangeTime = 8, expire = 60)
    public R addUser(@RequestBody UserModel user){
        System.out.println("user-->" + user.toString());

        if (! "".equals(user.getPassword()) && user.getPassword() != null) {

            user.setPassword(MD5Util.saltEncryption(user.getPassword()));

            System.out.println(user.getPassword() + "==" + user.getUserName());

            boolean flag = userService.saveOrUpdate(user);

            if (flag) {
                rabbitProducer.send(user);

                return R.ok().message("添加用户成功");
            }
        }

        return R.error().message("添加用户失败");
    }


}
