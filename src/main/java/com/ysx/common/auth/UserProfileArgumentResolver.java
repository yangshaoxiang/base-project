package com.ysx.common.auth;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ysx.common.response.BusinessException;
import com.ysx.common.response.ResponseCodeEnum;
import com.ysx.common.response.BusinessException;
import com.ysx.common.response.ResponseCodeEnum;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


import javax.annotation.Resource;

/**
 *  @Description: spring mvc 参数解析器 即根据自己的业务处理，将数据绑定到 Controller 中指定类型的参数对象中
 * @author ysx
 */
@Component
@Slf4j
public class UserProfileArgumentResolver implements HandlerMethodArgumentResolver {
    @Resource
    private JwtTokenService jwtTokenService;

    @Resource
    private JwtAuthProperties jwtAuthProperties;

    @Resource
    private ObjectMapper objectMapper;



    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clz = parameter.getParameterType();
        parameter.getParameterAnnotations();
        return TokenUserModel.class.isAssignableFrom(clz);
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)  {
        String token = webRequest.getHeader(jwtAuthProperties.getTokenHeader());
        boolean hasParameterAnnotation = parameter.hasParameterAnnotation(Anonymous.class);
        if (StrUtil.isBlank(token)) {
            //针对部分接口即支持会员访问又支持匿名访问拦截处理
            if (!hasParameterAnnotation) {
                throw new BusinessException(ResponseCodeEnum.ERROR_SYSTEM_NO_LOGIN);
            } else {
                return null;
            }
        } else {
            try {
                Claims claims = jwtTokenService.parseJwt(token);
                String subject = claims.getSubject();
                return objectMapper.readValue(subject, parameter.getParameterType());
            } catch (Exception e) {
                //针对部分接口即支持会员访问又支持匿名访问拦截处理
                if (hasParameterAnnotation) {
                    return null;
                }
                log.error("解析token异常:",e);
                throw new BusinessException(ResponseCodeEnum.ERROR_SYSTEM_NO_LOGIN);
            }
        }
    }

}