package com.ysx.common.frameworkext.requestbodyparam;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

@Configuration
public class RequestBodyParamConfig implements WebMvcConfigurer {

    @Resource
    private RequestBodyParamArgumentResolver requestBodyParamArgumentResolver;

    /**
     * 注册 过滤器
     */
    @Bean
    public FilterRegistrationBean repeatInputStreamFilter() {
        FilterRegistrationBean<RepeatInputStreamFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new RepeatInputStreamFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }


    /**
     *  配置 spring mvc 参数解析
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers){
        // RequestBodyParam 注解支持 参数解析
        resolvers.add(requestBodyParamArgumentResolver);
    }
}
