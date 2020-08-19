package com.ysx.common.sign;

import com.ysx.common.frameworkext.requestbodyparam.RepeatInputStreamHttpServletRequestWrapper;
import com.ysx.common.response.BusinessException;
import com.ysx.common.response.ResponseCodeEnum;
import com.ysx.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Map;
import java.util.Objects;

/**
 * @Description:  接口签名校验
 * @author ysx
 */
@Component
@Slf4j
public class SignInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        SimpleSign simpleSign = null;
        //  校验是否免签
        if (handler instanceof HandlerMethod) {
            SignIgnore signIgnore = ((HandlerMethod) handler).getMethodAnnotation(SignIgnore.class);
            if (signIgnore!=null&&signIgnore.value()) {
               return true;
            }
            simpleSign = ((HandlerMethod) handler).getMethodAnnotation(SimpleSign.class);

        }
        // 是否简单验签
        if(simpleSign == null){
            // 校验签名
            if(!checkSign(request)){
                throw new BusinessException(ResponseCodeEnum.ERROR_SYSTEM_SIGN_NOT_ACCESS);
            }
        }else{
            // 简单校验签名
            if(!checkSimpleSign(request)){
                throw new BusinessException(ResponseCodeEnum.ERROR_SYSTEM_SIGN_NOT_ACCESS);
            }
        }

        return true;
    }

    /**
     *  简单验签
     */
    private boolean checkSimpleSign(HttpServletRequest request) throws IOException {
        SignHeaderModel signHeaderModel = packageSignHeader(request);
        if(signHeaderModel.checkSignHeader()){
            return false;
        }
        String serverSign = SignUtil.sign(signHeaderModel,null, null, null);
        return Objects.equals(serverSign, signHeaderModel.getSignature());
    }

    private boolean checkSign(HttpServletRequest request) throws IOException {
        SignHeaderModel signHeaderModel = packageSignHeader(request);
        if(signHeaderModel.checkSignHeader()){
            return false;
        }

        //获取body（对应@RequestBody）
        String body = null;
        if (request instanceof RepeatInputStreamHttpServletRequestWrapper) {
            body = IOUtils.toString(request.getInputStream());
        }

        //获取parameters（对应@RequestParam）
        Map<String, String[]> params = null;
        if (!CollectionUtils.isEmpty(request.getParameterMap())) {
            params = request.getParameterMap();
        }

        //获取path variable（对应@PathVariable）
        String[] paths = null;
        ServletWebRequest webRequest = new ServletWebRequest(request, null);
        Map<String, String> uriTemplateVars = (Map<String, String>) webRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        if (!CollectionUtils.isEmpty(uriTemplateVars)) {
            paths = uriTemplateVars.values().toArray(new String[]{});
        }
        String serverSign = SignUtil.sign(signHeaderModel,body, params, paths);
        return Objects.equals(serverSign, signHeaderModel.getSignature());
    }


    private SignHeaderModel packageSignHeader(HttpServletRequest request){
        return WebUtil.packageHeaderToBean(SignHeaderModel.class,request);
    }

}
