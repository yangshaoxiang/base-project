package com.ysx.common.frameworkext.requestbodyparam;

import java.lang.annotation.*;

/**
 * @Description:
 * 该注解 用于 获取 客户端提交 json 数据 将json 数据中的某个字段，绑定到 Controller 中的参数列表上
 * eg:
 * 客户端 POST 方式传参:
 * {
 *     "phoneNum":"15867957671",
 *     "password":"123123",
 *     "user":{
 *         "account":{
 *             "name":"张三",
 *             "num":100
 *         }
 *     }
 * }
 * Controller 接收
 * public User login(@RequestBodyParam("password")String password,@RequestBodyParam("phoneNum") String phoneNum,, @RequestBodyParam("user.account.num") Integer num, @RequestBodyParam("user.account")Account account)
 * @author ysx
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Documented
public @interface RequestBodyParam {
    /**
     * json 串中对应的字段名称 不写会取 Controller 方法中参数名称 但是可能会取不到(编译后的 LocalVariableTable信息 未包含参数信息),因此建议这个字段写上并和json字段名一致
     */
    String value() default "";

    /**
     * 忽略抓取 指定的属性，直接将接收到的 json 串转化为 对应的参数 即设置为true， 功能 和 @RequestBody 相同，一般无使用场景(因为使用 @RequestBody 即可 无需使用该属性)
     *  若有场景需要 使用本功能 注意 swagger 文档 不会识别本注解为 json 形式参数，@Valid 注解功能会无效
     */
    boolean directConversion() default false;


}
