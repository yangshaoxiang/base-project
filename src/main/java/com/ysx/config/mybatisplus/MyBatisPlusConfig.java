package com.ysx.config.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 *  @Description: mybatis-plus 配置
 * @author ysx
 */
@EnableTransactionManagement
@Configuration
public class MyBatisPlusConfig {

    /**
     * 注册乐观锁插件
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }


    /**
     *  注册分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return  new PaginationInterceptor();
    }




}
