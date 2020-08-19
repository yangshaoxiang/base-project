package com.ysx.common.log;

import cn.hutool.json.JSONUtil;
import com.ysx.common.constant.EnvironmentConstant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:  打印请求日志
 * @Author: ysx
 */
@Component
@Aspect
@Slf4j
@Profile(EnvironmentConstant.ENV_NEVER)
public class RequestLogAspect {

    @Pointcut("execution(* com.ysx.controller..*(..))")
    public void requestServer() {
    }

    @Around("requestServer()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setRequestParams(getRequestParamsByProceedingJoinPoint(proceedingJoinPoint));
        requestInfo.setIp(getRealIp(request));
        requestInfo.setUrl(request.getRequestURL().toString());
        requestInfo.setHttpMethod(request.getMethod());
        requestInfo.setClassMethod(String.format("%s.%s", proceedingJoinPoint.getSignature().getDeclaringTypeName(),
                proceedingJoinPoint.getSignature().getName()));

        // 执行 Controller 方法
        Object result = proceedingJoinPoint.proceed();


        // 记录结果 耗时
        requestInfo.setResult(result);
        requestInfo.setTimeCost(System.currentTimeMillis() - start);
        log.info("Request Info      : {}", JSONUtil.toJsonStr(requestInfo));

        return result;
    }


    @AfterThrowing(pointcut = "requestServer()", throwing = "e")
    public void doAfterThrow(JoinPoint joinPoint, RuntimeException e) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        RequestErrorInfo requestErrorInfo = new RequestErrorInfo();
        requestErrorInfo.setIp(request.getRemoteAddr());
        requestErrorInfo.setUrl(request.getRequestURL().toString());
        requestErrorInfo.setHttpMethod(request.getMethod());
        requestErrorInfo.setClassMethod(String.format("%s.%s", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName()));
        requestErrorInfo.setRequestParams(getRequestParamsByJoinPoint(joinPoint));
        requestErrorInfo.setException(e);
        log.info("Error Request Info      : {}", JSONUtil.toJsonStr(requestErrorInfo));
    }

    /**
     * 获取入参
     * */
    private Map<String, Object> getRequestParamsByProceedingJoinPoint(ProceedingJoinPoint proceedingJoinPoint) {
        //参数名
        String[] paramNames = ((MethodSignature)proceedingJoinPoint.getSignature()).getParameterNames();
        //参数值
        Object[] paramValues = proceedingJoinPoint.getArgs();

        return buildRequestParam(paramNames, paramValues);
    }

    private Map<String, Object> getRequestParamsByJoinPoint(JoinPoint joinPoint) {
        //参数名
        String[] paramNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        //参数值
        Object[] paramValues = joinPoint.getArgs();

        return buildRequestParam(paramNames, paramValues);
    }

    private Map<String, Object> buildRequestParam(String[] paramNames, Object[] paramValues) {
        Map<String, Object> requestParams = new HashMap<>(16);
        for (int i = 0; i < paramNames.length; i++) {
            Object value = paramValues[i];

            //如果是文件对象
            if (value instanceof MultipartFile) {
                MultipartFile file = (MultipartFile) value;
                //获取文件名
                value = file.getOriginalFilename();  
            }
            // 注意 这里将 参数值 转为了 json，原因是 如果使用原始参数对象，对象值在 Controller 方法中做了修改，那么这里将不准确
            requestParams.put(paramNames[i], JSONUtil.toJsonStr(value));
        }
        return requestParams;
    }


    /**
     * 获取请求用户远程主机ip地址
     */
    private  String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        boolean ipUnKnow = "unknown".equalsIgnoreCase(ip );

        if (ip == null || ip.length() == 0 || ipUnKnow) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ipUnKnow) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ipUnKnow) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || ipUnKnow) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || ipUnKnow) {
            ip = request.getRemoteAddr();
        }
        // 如果是多级代理，那么取第一个ip为客户端ip
        String split = ",";
        if (ip != null && ip.contains(split)) {
            ip = ip.substring(0, ip.indexOf(split)).trim();
        }
        return ip;
    }

    @Data
    private class RequestInfo {
        private String ip;
        private String url;
        private String httpMethod;
        private String classMethod;
        private Object requestParams;
        private Object result;
        private Long timeCost;
    }

    @Data
    private class RequestErrorInfo {
        private String ip;
        private String url;
        private String httpMethod;
        private String classMethod;
        private Object requestParams;
        private RuntimeException exception;
    }
}