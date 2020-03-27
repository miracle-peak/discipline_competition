package com.gxuwz.subject.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gxuwz.subject.model.UserModel;
import com.gxuwz.subject.service.IUserService;
import com.gxuwz.subject.util.*;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Map;

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
    private JedisUtil jedistUtil;

    @RequestMapping("/login")
//    @ApiOperation("登录")
    public R login(@RequestBody UserModel userModel){
//    public R login(@RequestBody Map<String,Object> params){
//    public R login(@RequestParam("username") String userName, @RequestParam("password") String password){
        System.out.println("login--->");

//        String userName = "";
//        String password = "";

        /*if (params.containsKey("userName")){
            userName = (String)params.get("userName");
        }
        if (params.containsKey("password")) {
            password = (String) params.get("password");
        }*/
        if (userModel == null){
            return R.error().message("啥的没有");
        }
        String userName = userModel.getUserName();
        String password = userModel.getPassword();

        System.out.println("========" + userName + "**&&" + password);

        if(userName.equals("") || userName == null){
            return R.error().message("请输入用户名");
        }else if (password.equals("") || password == null){
            return R.error().message("请输入密码");
        }

        password = MD5Util.saltEncryption(password);

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_name", userName);
        wrapper.eq("password", password);


        System.out.println("user:" + userName);

        UserModel one = userService.getOne(wrapper);
        if (one != null){
            String token = JWTUtil.createToken(one.getId() + "", one.getUserName(), one.getUtype());

            boolean flag = jedistUtil.setToken(one.getId() + "", token, 60 * 60 * 24 * 6);

            if (flag){
                return R.ok().message("登录成功");
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
