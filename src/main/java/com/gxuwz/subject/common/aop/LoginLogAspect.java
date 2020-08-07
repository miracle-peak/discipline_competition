package com.gxuwz.subject.common.aop;

import com.gxuwz.subject.common.util.IpUtil;
import com.gxuwz.subject.common.util.R;
import com.gxuwz.subject.model.LoginLogModel;
import com.gxuwz.subject.model.UserModel;
import com.gxuwz.subject.service.ILoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import qiniu.happydns.util.IP;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;


/**
 * @author: 蔡奇峰
 * date: 2020/4/17 16:06
 **/
@Slf4j
@Component
@Aspect
public class LoginLogAspect {

    @Autowired
    private ILoginLogService logService;

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 切入点位置
     */
    @Pointcut("execution(public * com.gxuwz.subject.*Controller.login(..))")
    public void loginWebLog() {
    }

    /**
     * 切入点位置
     */
    @Pointcut("execution(public * com.gxuwz.subject.service.*Services.*(..))")
    public void operateWebLog() {
    }

    @Pointcut("@annotation(com.gxuwz.subject.common.annotation.Log)")
    public void saveLog() {
    }


    /**
     * 保存登录记录（登录日志）
     * 包含登录时间、用户名、ip、登录返回的msg、登录状态（是否登录成功）
     *
     * @param joinPoint
     * @param result    切入点方法返回的对象
     */
    @AfterReturning(value = "saveLog()", returning = "result")
    public void saveLog(JoinPoint joinPoint, Object result) {
        LoginLogModel logModel = new LoginLogModel();

        boolean isSuccess = false;
        String msg = "";
        if (result instanceof R) {
            R r = (R) result;
            isSuccess = r.isSuccess();

            msg = r.getMessage();
        }

        //从切面织入点处通过反射机制获取织入点处的方法
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        //获取切入点所在的方法
//        Method method = signature.getMethod();

        // 请求的参数
        Object[] args = joinPoint.getArgs();

        UserModel userModel = null;
        if (args[0] instanceof UserModel) {
            userModel = (UserModel) args[0];
            logModel.setUserName(userModel.getUserName());
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        // 获取用户ip地址
        String ip = IpUtil.getIpAddress(request);
        // 获取城市信息
        String cityInfo = IpUtil.getCityInfo(ip);

        String status = isSuccess ? "0" : "1";

        // 设置时区, 解决部署服务器时间不对
        LocalDateTime time = LocalDateTime.now(ZoneId.of("UTC+8"));

        // 使用@Builder注解
        logModel.setIp(ip)
                .setMsg(msg + "--city: " + cityInfo)
                .setStatus(status)
                .setLoginTime(time + "");

        log.info("login log:{}", logModel.toString());

        boolean isSave = logService.save(logModel);

        if (!isSave) {
            log.error("保存登录日志失败！");
        }

    }


}
