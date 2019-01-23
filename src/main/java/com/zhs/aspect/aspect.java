package com.zhs.aspect;

import com.zhs.annotation.Log;
import com.zhs.entity.SysLog;
import com.zhs.entity.SysUser;
import com.zhs.service.ISysLogService;
import com.zhs.utils.HttpContextUtils;
import com.zhs.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

/**
 * @project: JPA
 * @author: zhs
 * @date: 2018/11/8 17:02
 * @package: com.qq
 * @description:
 */
@Aspect
@Component
@Slf4j
public class aspect {


    @Autowired
    ISysLogService logService;


    @Pointcut("@annotation(com.zhs.annotation.Log)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //异步保存日志
        saveLog(point, time);
        return result;
    }

    void saveLog(ProceedingJoinPoint joinPoint, long time) throws InterruptedException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = new SysLog();
        Log syslog = method.getAnnotation(Log.class);
        if (syslog != null) {
            // 注解上的描述
            sysLog.setOperation(syslog.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        Object[] args = joinPoint.getArgs();
        System.out.println(Arrays.toString(joinPoint.getArgs())+"============================");
        // 请求的参数
        //   sysLog.setParams(Arrays.toString(joinPoint.getArgs()));
       //* String params = JSONUtils.beanToJson(args[0]).substring(0, 4999);
        //sysLog.setParams(params);
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));
        // 用户名

        SysUser currSysUser =(SysUser) SecurityUtils.getSubject().getPrincipal();
        if (null == currSysUser) {
            if (null != sysLog.getParams()) {
                sysLog.setUsername(sysLog.getParams());
            } else {
                sysLog.setUsername("获取用户信息为空");
            }
        } else {
            sysLog.setUsername(currSysUser.getUsername());
        }
        sysLog.setTime((int) time);
        // 系统当前时间
        Date date = new Date();
        sysLog.setGmtcreate(date);
        // 保存系统日志
        logService.insertOperlog(sysLog);
    }
}
