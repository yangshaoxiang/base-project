package com.ysx.common.frameworkext.jsonignore;

import java.lang.annotation.*;

/**
 * @Description: 忽略 Controller 方法上返回值对像的部分字段
 * @Author: ysx
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface JsonFieldIgnore {
    String[] value();
}
