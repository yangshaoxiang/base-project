package com.ysx.common.sign;

import com.ysx.common.constant.EnvironmentConstant;
import com.ysx.common.constant.EnvironmentConstant;
import com.ysx.common.constant.EnvironmentConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.annotation.Resource;

/**
 * @Description: 项目接口签名配置 主要全局配置需要签名的接口
 * @author ysx
 */
@Configuration
@Profile(EnvironmentConstant.ENV_DEV)
public class SignConfig implements WebMvcConfigurer {

    @Resource
    private SignInterceptor signInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signInterceptor).addPathPatterns("/baseproject/api/system/test/**");
                //.excludePathPatterns("/api/ignoreSign");
    }


}
