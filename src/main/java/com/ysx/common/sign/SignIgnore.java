package com.ysx.common.sign;

import java.lang.annotation.*;

/**
 * 忽略接口签名认证 用于 Controller 方法上
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SignIgnore {
    boolean value() default true;
}
