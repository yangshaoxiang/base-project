package com.ysx.config;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  @Description: jackson 全局相关配置
 * @author ysx
 */
@Configuration
public class JackJsonConfig {
    @Bean
    public ObjectMapper  objectMapper (){
        ObjectMapper objectMapper = new ObjectMapper();
        //序列化的时候序列对象的所有属性
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //反序列化的时候如果多了其他属性,不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //如果是空对象的时候,不抛异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 设置过滤器 对所有的 Object 子类生效
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("myFilter",SimpleBeanPropertyFilter.serializeAll());
        objectMapper.addMixIn(Object.class,MyFilter.class).setFilterProvider(filterProvider);
        return objectMapper;
    }

    /**
     * 设置 Filter 类或接口
     */
    @JsonFilter("myFilter")
    interface MyFilter {}


}
