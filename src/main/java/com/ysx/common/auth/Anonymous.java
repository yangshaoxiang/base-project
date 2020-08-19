package com.ysx.common.auth;

import java.lang.annotation.*;

/**
 * 匿名处理
 *  正常 Controller 方法中 包含 TokenUserModel 对象 即为需要登录，但是可能存在特殊情况，即
 *  针对部分接口即支持会员访问又支持匿名访问拦截处理
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Documented
public @interface Anonymous {

}
