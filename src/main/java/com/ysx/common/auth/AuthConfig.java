package com.ysx.common.auth;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 *  配置 auth 参数解析器
 */
@Configuration
public class AuthConfig implements WebMvcConfigurer {
    @Resource
    private UserProfileArgumentResolver userProfileArgumentResolver;

    /**
     *  配置 spring mvc 参数解析
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers){
        // 用户 认证参数解析
        resolvers.add(userProfileArgumentResolver);
    }






}
