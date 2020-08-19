package com.ysx.config;

import com.ysx.common.auth.TokenUserModel;
import com.ysx.common.constant.EnvironmentConstant;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 在线接口文档配置 文档地址 http://ip:port/doc.html#/home
 * @author ysx
 */
@Configuration
@EnableSwagger2
@Profile({EnvironmentConstant.ENV_DEV,EnvironmentConstant.ENV_UAT,EnvironmentConstant.ENV_LOCAL})
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class Knife4jConfig {
    @Value("${ysx.auth.tokenHeader}")
    private String tokenHeader;
    @Bean
    public Docket createRestApi() {
        // 设置 swagger ui 可以选填 token 请求头参数
        List<Parameter> pars = new ArrayList<>();
        if(tokenHeader!=null){
            ParameterBuilder ticketPar = new ParameterBuilder();
            ticketPar.name(tokenHeader).description("登录凭证(选填)")
                    //header中的 token 参数非必填，传空也可以
                    .modelRef(new ModelRef("string")).parameterType("header")
                    .required(false)
                    .build();
            pars.add(ticketPar.build());
        }

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //.apis(RequestHandlerSelectors.basePackage("com.ysx.controller")) //你需要生成文档所在的包
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).paths(PathSelectors.any())
                .paths(PathSelectors.any())
                .build()
                // 忽略 该种类型的参数在 文档中的展示 同 @ApiIgnore 注解
                .ignoredParameterTypes(TokenUserModel.class)
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //文档标题
                .title("基础 api 文档")
                .description("在线调试接口")
                .termsOfServiceUrl("https://doc.xiaominfo.com/")
                .version("1.0")
                .build();
    }
}