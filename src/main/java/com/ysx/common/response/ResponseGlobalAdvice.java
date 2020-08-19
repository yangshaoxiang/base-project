package com.ysx.common.response;


import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import java.lang.reflect.Type;
import java.util.Date;


/**
 * @Description:  统一封装响应结果 将 Controller 的结果 统一用 ResultDto 在外面包一层
 * @Author: ysx
 */
@RestControllerAdvice(basePackages = {"com.ysx.controller"})
@Order(10)
public class ResponseGlobalAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass) {
        // 如果接口返回的类型本身就是 ResultDto 那就没有必要进行额外的操作，返回false
        return !returnType.getGenericParameterType().getTypeName().startsWith(ResultDto.class.getName());
    }

    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest request, ServerHttpResponse response) {
        Type genericParameterType = returnType.getGenericParameterType();
        // 对基本类型包装，包装到对象中
        if(genericParameterType instanceof Class && data!=null){
            Class clsss  = (Class)genericParameterType;
            if(isBaseType(clsss)){
                ProcessBaseType processBaseType = new ProcessBaseType();
                processBaseType.setResult(data);
                return ResultDto.success(processBaseType);
            }
        }
        return ResultDto.success(data);
    }




    /**
     * 判断类型是否是不可拆分的几种常见基本类型(不常见的未包含其中)
     * 包含: 基本类型，基本类型包装类型,void，String,Date类型，
     *
     * @param clazz 要判断的类型
     * @return 不是以上几种基本类型
     */
    private static boolean isBaseType(Class<?> clazz) {
        return clazz.isPrimitive() || isBaseReferenceType(clazz) || String.class.isAssignableFrom(clazz) || Date.class.isAssignableFrom(clazz) || void.class.isAssignableFrom(clazz);
    }

    /**
     * 判断class是否基本引用类型
     *
     * @param clazz 要判断的class对象
     * @return 是否基本引用类型
     */
    private static boolean isBaseReferenceType(Class clazz) {
        return Number.class.isAssignableFrom(clazz) || Boolean.class.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz);
    }

}
