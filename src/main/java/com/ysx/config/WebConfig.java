package com.ysx.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.Date;
import java.util.List;


/**
 *  web 公用配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 跨域请求设置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOrigins("*")
                //是否允许证书 不再默认开启
                .allowCredentials(true)
                //设置允许的方法
                .allowedMethods("*")
                //跨域允许时间
                .maxAge(3600);
    }

    /**
     * 配置 普通get请求传参 时间戳和日期映射
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new Long2DateConverter());
    }
    public class Long2DateConverter implements Converter<String, Date> {
        @Override
        public Date convert(String ts) {
            if (!StringUtils.hasText(ts)) {
                return null;
            }
            return new Date(Long.valueOf(ts));
        }
    }

    /**
     *  配置自定义 HttpMessageConverter 调整顺序 （将 MappingJackson2HttpMessageConverter 放在最前面）
     * @param converters 系统中已有的 HttpMessageConverter
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (int i = 0; i < converters.size(); i++) {
            HttpMessageConverter<?> httpMessageConverter = converters.get(i);
            if (httpMessageConverter instanceof MappingJackson2HttpMessageConverter) {
                // 将 MappingJackson2HttpMessageConverter 放在最前面
                converters.remove(i);
                converters.add(0,httpMessageConverter);
                break;
            }
        }
    }




}
