package com.ysx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;



/**
 * @Description: 项目主启动类
 * @author ysx
 */
@SpringBootApplication
@MapperScan("com.ysx.mapper")
@EnableCaching
@EnableAspectJAutoProxy(exposeProxy = true)
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}


