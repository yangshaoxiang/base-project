package com.ysx.common.frameworkext.requestbodyparam;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;


/**
 * @Description:  spring mvc 参数解析器 即根据自己的业务处理，将数据绑定到 Controller 中指定类型的参数对象中
 * @author ysx
 */
@Component
@Slf4j
public class RequestBodyParamArgumentResolver implements HandlerMethodArgumentResolver {
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestBodyParam.class);
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 优先 取注解 配置的字段名称
        RequestBodyParam parameterAnnotation = parameter.getParameterAnnotation(RequestBodyParam.class);
        String paramName = parameterAnnotation.value();
        // 注解 未配置字段名称 则 使用 Controller 方法参数名 可能会有问题 因为并不是绝对可以取到 (编译后的 LocalVariableTable信息 未包含参数信息)
        if("".equals(paramName)){
            paramName = parameter.getParameterName();
        }
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        ServletServerHttpRequest inputMessage = new ServletServerHttpRequest(servletRequest);
        MediaType contentType = inputMessage.getHeaders().getContentType();
        //  contentType 为空 表明 非 application/json 形式传参，做普通键值对形式传参的兼容
        if(contentType == null ||!contentType.getSubtype().contains("json")){
            if (binderFactory != null) {
                WebDataBinder binder = binderFactory.createBinder(webRequest, null, paramName);
                return binder.convertIfNecessary(servletRequest.getParameter(paramName), parameter.getParameterType(), parameter);
            }
            return null;
        }
        String subtype = contentType.getSubtype().toLowerCase();
        if("json".equals(subtype)){
            try {
                InputStream body = inputMessage.getBody();
                JsonNode innerNode = objectMapper.readTree(body);
                if(!parameterAnnotation.directConversion()){
                    // 如果 注解参数包含 . 则表明是需要解析获取 json 参数中的深层参数
                    String[] jsonInnerParams = paramName.split("\\.");
                    if(jsonInnerParams.length > 0){
                        for (String jsonInnerParam : jsonInnerParams) {
                            innerNode = innerNode.get(jsonInnerParam);
                        }
                    }else{
                        // 不包含 . 正常 获取
                        innerNode = innerNode.get(paramName);
                    }
                }
                return objectMapper.treeToValue(innerNode, parameter.getParameterType());
            } catch (IOException e) {
                log.error("解析 @RequestBodyParam 注解 参数绑定异常",e);
            }
        }
        return null;
    }

}