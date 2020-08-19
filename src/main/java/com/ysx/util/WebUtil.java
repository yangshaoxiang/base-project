package com.ysx.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * @Description: web 相关的工具类
 * @Author: ysx
 */
@Slf4j
public class WebUtil {


    /**
     *  将 header 头中属性赋值给指定的对象
     * @param t 要赋值的对象
     * @param request 本次请求对象
     */
    public static <T> void packageHeaderToBean(T t, HttpServletRequest request){
        try{
            BeanInfo beanInfo = Introspector.getBeanInfo(t.getClass(),Object.class);
            PropertyDescriptor[] proDescrtptors = beanInfo.getPropertyDescriptors();
            if(proDescrtptors != null && proDescrtptors.length > 0){
                for(PropertyDescriptor propDesc:proDescrtptors){
                    Method methodSet = propDesc.getWriteMethod();
                    methodSet.invoke(t, request.getHeader(propDesc.getName()));
                }
            }
        }catch (Exception e) {
            log.error("封装 请求头数据 异常",e);
        }
    }

    /**
     *  将 header 头中属性赋值给指定的类型 方法会自动创建对象(注意指定的类型必须有无参构造方法)
     * @param t 要赋值的对象类型
     * @param request 本次请求对象
     * @return 生成的赋完值后产生的对象
     */
    public static <T> T packageHeaderToBean(Class<T> t, HttpServletRequest request){
        T instance = null;
        try{
            instance = t.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(t.getClass(),Object.class);
            PropertyDescriptor[] proDescrtptors = beanInfo.getPropertyDescriptors();
            if(proDescrtptors != null && proDescrtptors.length > 0){
                for(PropertyDescriptor propDesc:proDescrtptors){
                    Method methodSet = propDesc.getWriteMethod();
                    methodSet.invoke(instance, request.getHeader(propDesc.getName()));
                }
            }
        }catch (Exception e) {
            log.error("封装 请求头数据 异常",e);
        }
        return instance;
    }
}
