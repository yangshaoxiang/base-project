package com.ysx.config.pagehelp;
import com.github.pagehelper.PageInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.Resource;
import java.util.Properties;

/**
 *  PageHelper 分页插件配置
 *
 * @author ysx
 */
@Configuration
@EnableConfigurationProperties(PageHelperProperties.class)
public class PageHelperAutoConfiguration {

    @Resource
    private PageHelperProperties properties;

    /**
     * 接受分页插件额外的属性
     */
    @Bean
    @ConfigurationProperties(prefix = PageHelperProperties.PAGEHELPER_PREFIX)
    public Properties pageHelperProperties() {
        return new Properties();
    }


    @Bean
    public PageInterceptor pageInterceptor() {
        PageInterceptor interceptor = new PageInterceptor();
        Properties properties = new Properties();
        //先把一般方式配置的属性放进去
        properties.putAll(pageHelperProperties());
        //在把特殊配置放进去，由于close-conn 利用上面方式时，属性名就是 close-conn 而不是 closeConn，所以需要额外的一步
        properties.putAll(this.properties.getProperties());
        interceptor.setProperties(properties);
        return interceptor;
    }

}
