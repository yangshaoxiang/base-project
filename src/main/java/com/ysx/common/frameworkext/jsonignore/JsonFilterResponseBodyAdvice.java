
package com.ysx.common.frameworkext.jsonignore;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import java.lang.reflect.Type;
import java.util.Date;
/**
 * @Description:  过滤 controller 响应 json 中的字段  注意 配置全局 ObjectMapper
 *                  设置 objectMapper.addMixIn(Object.class,MyFilter.class)，否则需要在过滤的实体类上添加 @JsonFilter("任意名称")
 * @Author: ysx
 */
@RestControllerAdvice
@Order(11)
public class JsonFilterResponseBodyAdvice extends AbstractMappingJacksonResponseBodyAdvice {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		Type genericParameterType = returnType.getGenericParameterType();
		// 获取类型
		Class parameterType;
		// 一般类型
		if(genericParameterType instanceof Class){
			parameterType = (Class) genericParameterType;
		}else if(genericParameterType instanceof ParameterizedTypeImpl){
			// 集合类型
			parameterType = ((ParameterizedTypeImpl) genericParameterType).getRawType();
		}else{
			// 其他类型不支持
			return false;
		}
		return super.supports(returnType, converterType) && returnType.hasMethodAnnotation(JsonFieldIgnore.class) && !isBaseType(parameterType);
	}

	@Override
	protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType,
			MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
		JsonFieldIgnore resultFieldIgnore = returnType.getMethod().getAnnotation(JsonFieldIgnore.class);

		Assert.state(resultFieldIgnore != null, "No JsonFieldIgnore annotation");

		String[] filterFields = resultFieldIgnore.value();
		if (filterFields.length == 0) {
			throw new IllegalArgumentException(
					"@JsonFieldIgnore must  at least 1 filter filed,now @JsonFieldIgnore value is empty");
		}
		// 设置属性过滤器
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.setDefaultFilter(SimpleBeanPropertyFilter.serializeAllExcept(filterFields));
		bodyContainer.setFilters(filterProvider);
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
