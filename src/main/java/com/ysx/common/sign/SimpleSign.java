package com.ysx.common.sign;

import java.lang.annotation.*;

/**
 * @Description: 标识方法简单验签(只使用请求头，不使用相关参数) 用于 Controller 方法上
 * @author ysx
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SimpleSign {
}
